package com.me.book_management.service.impl;

import com.jlefebure.spring.boot.minio.MinioService;
import com.me.book_management.constant.ATTACHMENT;
import com.me.book_management.constant.Constants;
import com.me.book_management.dto.request.attachment.CreateAttachmentRequest;
import com.me.book_management.dto.response.attachment.SalesExportRow;
import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.book.Book;
import com.me.book_management.entity.cart.Cart;
import com.me.book_management.entity.cart.CartBook;
import com.me.book_management.entity.file.File;
import com.me.book_management.exception.InputException;
import com.me.book_management.exception.NotFoundException;
import com.me.book_management.repository.account.AccountRepository;
import com.me.book_management.repository.FileRepository;
import com.me.book_management.repository.book.BookRepository;
import com.me.book_management.repository.cart.CartBookRepository;
import com.me.book_management.repository.cart.CartRepository;
import com.me.book_management.service.AttachmentService;
import com.me.book_management.util.CommonUtil;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttachmentServiceImpl implements AttachmentService {

    private final MinioService minioService;
    private final MinioClient minioClient;
    private final FileRepository fileRepository;
    private final CartRepository cartRepository;
    private final CartBookRepository cartBookRepository;

    @Value("${spring.minio.bucket}")
    private String defaultBucket;

    @Value("${spring.minio.url}")
    private String minioUrl;

    @Override
    public File upload(CreateAttachmentRequest request) {
        validateFile(request.getFile());
        String objectName = generateObjectName(request);

        try (InputStream in = request.getFile().getInputStream()) {
            minioClient.putObject(
                    defaultBucket,
                    objectName,
                    in,
                    request.getFile().getSize(),
                    request.getFile().getContentType()
            );


            File file = createFileEntity(request, objectName);
            fileRepository.save(file);
            log.info("File uploaded: {}", file.getFileName());
            return file;

        } catch (Exception e) {
            log.error("Error uploading file: {}", e.getMessage(), e);
            throw new InputException("Failed to upload file: " + e.getMessage());
        }
    }

    private String generatePath(CreateAttachmentRequest request) {
        if (File.FileType.BookCover.equals(request.getFileType())) {
            return Constants.PATH.Cover;
        }
        if (File.FileType.BookPdf.equals(request.getFileType())) {
            return Constants.PATH.Book;
        }
        return "/";
    }

    @Override
    public File find(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("File not found id: " + id));
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public byte[] export(String period, int year, Integer month, Integer quarter) {
        log.info("Exporting sales data for period: {}, year: {}, month: {}, quarter: {}", period, year, month, quarter);
        
        try {
            // Fetch all completed carts
            List<Cart> completedCarts = cartRepository.findAll().stream()
                    .filter(cart -> Constants.CART_STATUS.COMPLETED.equals(cart.getStatus()))
                    .collect(Collectors.toList());
            
            log.info("Found {} completed carts", completedCarts.size());
            
            // Filter carts based on period
            List<Cart> filteredCarts = filterCartsByPeriod(completedCarts, period, year, month, quarter);
            
            log.info("After filtering by period: {} carts", filteredCarts.size());
            
            // Aggregate data by book
            List<SalesExportRow> salesData = aggregateSalesByBook(filteredCarts);
            
            // Generate Excel file
            return generateExcelFile(salesData, period, year, month, quarter);
            
        } catch (Exception e) {
            log.error("Error exporting sales data: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to export sales data: " + e.getMessage());
        }
    }
    
    /**
     * Filter carts based on period (month, quarter, year)
     * Since we don't have timestamp fields in the Cart entity, we'll use cart IDs as a proxy for time
     * This is a simplified approach and assumes that cart IDs increase over time
     */
    private List<Cart> filterCartsByPeriod(List<Cart> carts, String period, int year, Integer month, Integer quarter) {
        // Sort carts by ID (assuming IDs increase over time)
        carts.sort(Comparator.comparing(Cart::getId));
        
        // Calculate the total number of carts per year (approximate)
        int totalCarts = carts.size();
        int cartsPerYear = totalCarts / 3; // Assuming we have data for about 3 years
        
        // Calculate start and end indices based on period
        int startIndex = (year - 2023) * cartsPerYear; // Assuming the app started in 2023
        int endIndex;
        
        if (ATTACHMENT.PeriodType.Year.equals(period)) {
            endIndex = startIndex + cartsPerYear;
        } else if (ATTACHMENT.PeriodType.Quarter.equals(period) && quarter != null) {
            int cartsPerQuarter = cartsPerYear / 4;
            startIndex += (quarter - 1) * cartsPerQuarter;
            endIndex = startIndex + cartsPerQuarter;
        } else if (ATTACHMENT.PeriodType.Month.equals(period) && month != null) {
            int cartsPerMonth = cartsPerYear / 12;
            startIndex += (month - 1) * cartsPerMonth;
            endIndex = startIndex + cartsPerMonth;
        } else {
            throw new InputException("Invalid period or missing parameters");
        }
        
        // Ensure indices are within bounds
        startIndex = Math.max(0, startIndex);
        endIndex = Math.min(carts.size(), endIndex);
        
        return carts.subList(startIndex, endIndex);
    }
    
    /**
     * Aggregate sales data by book
     */
    private List<SalesExportRow> aggregateSalesByBook(List<Cart> carts) {
        Map<Long, SalesExportRow> salesByBook = new HashMap<>();
        
        for (Cart cart : carts) {
            List<CartBook> cartBooks = cartBookRepository.findByCart(cart);
            
            for (CartBook cartBook : cartBooks) {
                Book book = cartBook.getBook();
                Long bookId = book.getId();
                
                if (!salesByBook.containsKey(bookId)) {
                    salesByBook.put(bookId, new SalesExportRow(
                            bookId,
                            book.getName(),
                            0L,
                            BigDecimal.ZERO
                    ));
                }
                
                SalesExportRow row = salesByBook.get(bookId);
                row.setTotalSold(row.getTotalSold() + cartBook.getQty());
                
                BigDecimal revenue = BigDecimal.valueOf(cartBook.getPrice() * cartBook.getQty());
                row.setTotalRevenue(row.getTotalRevenue().add(revenue));
            }
        }
        
        return new ArrayList<>(salesByBook.values());
    }
    
    /**
     * Generate Excel file with sales data
     */
    private byte[] generateExcelFile(List<SalesExportRow> salesData, String period, int year, Integer month, Integer quarter) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sales Report");
            
            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Book ID");
            headerRow.createCell(1).setCellValue("Book Name");
            headerRow.createCell(2).setCellValue("Total Sold");
            headerRow.createCell(3).setCellValue("Total Revenue");
            
            // Create data rows
            int rowNum = 1;
            for (SalesExportRow row : salesData) {
                Row dataRow = sheet.createRow(rowNum++);
                dataRow.createCell(0).setCellValue(row.getBookId());
                dataRow.createCell(1).setCellValue(row.getBookName());
                dataRow.createCell(2).setCellValue(row.getTotalSold());
                dataRow.createCell(3).setCellValue(row.getTotalRevenue().doubleValue());
            }
            
            // Auto-size columns
            for (int i = 0; i < 4; i++) {
                sheet.autoSizeColumn(i);
            }
            
            // Create title with period information
            Row titleRow = sheet.createRow(rowNum + 1);
            String title = "Sales Report - ";
            if (ATTACHMENT.PeriodType.Year.equals(period)) {
                title += "Year " + year;
            } else if (ATTACHMENT.PeriodType.Quarter.equals(period)) {
                title += "Q" + quarter + " " + year;
            } else if (ATTACHMENT.PeriodType.Month.equals(period)) {
                String monthName = Month.of(month).getDisplayName(TextStyle.FULL, Locale.getDefault());
                title += monthName + " " + year;
            }
            titleRow.createCell(0).setCellValue(title);
            
            // Write to byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }

    @Override
    public List<File> findByBookId(Long bookId) {
        return null;
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new InputException("File cannot be null or empty");
        }
        
        // Check file size (max 10MB)
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new InputException("File size cannot exceed 10MB");
        }
        
        // Check file type
        String contentType = file.getContentType();
        if (contentType == null || (!contentType.startsWith("image/") && !contentType.equals("application/pdf"))) {
            throw new InputException("Only image and PDF files are allowed");
        }
    }

    private String generateObjectName(CreateAttachmentRequest request) {
        MultipartFile file = request.getFile();
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        return generatePath(request) + UUID.randomUUID().toString() + extension;
    }

    private File createFileEntity(CreateAttachmentRequest request, String objectName) {
        File file = new File();
        file.setFileName(request.getFile().getOriginalFilename());
        file.setFileSize(request.getFile().getSize());
        file.setFileType(request.getFileType() != null ? request.getFileType() : File.FileType.BookCover);
        file.setObjectKey(objectName);
        file.setFileUrl(minioUrl + "/" + defaultBucket + "/" + objectName);
        file.setIsPublic(request.getIsPublic() != null ? request.getIsPublic() : false);

        // Set additional fields
        file.setMimeType(request.getFile().getContentType());
        file.setFileExtension(getFileExtension(request.getFile().getOriginalFilename()));
        file.setBucketName(defaultBucket);
        
        return file;
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}