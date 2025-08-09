package com.me.book_management.service.impl;

import com.jlefebure.spring.boot.minio.MinioService;
import com.me.book_management.constant.Constants;
import com.me.book_management.dto.request.attachment.CreateAttachmentRequest;
import com.me.book_management.entity.file.File;
import com.me.book_management.exception.InputException;
import com.me.book_management.exception.NotFoundException;
import com.me.book_management.repository.FileRepository;
import com.me.book_management.repository.cart.CartBookRepository;
import com.me.book_management.repository.cart.CartRepository;
import com.me.book_management.service.AttachmentService;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

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