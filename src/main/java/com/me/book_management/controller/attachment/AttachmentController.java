package com.me.book_management.controller.attachment;

import com.me.book_management.constant.ATTACHMENT;
import com.me.book_management.dto.request.attachment.CreateAttachmentRequest;
import com.me.book_management.entity.file.File;
import com.me.book_management.service.AttachmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/attachments/")
@RequiredArgsConstructor
@Slf4j
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> uploadFile(@Valid @ModelAttribute CreateAttachmentRequest request) {
        try {
            MultipartFile file = request.getFile();

            File fileEntity = attachmentService.upload(request);
            
            Map<String, Object> response = new HashMap<>();
            response.put("id", fileEntity.getId());
            response.put("fileName", file.getOriginalFilename());
            response.put("fileSize", file.getSize());
            response.put("fileType", file.getContentType());
            
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error uploading file: {}", e.getMessage());
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            attachmentService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error deleting file: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Export sales data to Excel file
     * @param period The period type (Month, Quarter, Year)
     * @param year The year
     * @param month The month (required if period is Month)
     * @param quarter The quarter (required if period is Quarter)
     * @return Excel file as downloadable response
     */
    @GetMapping("export")
    public ResponseEntity<byte[]> exportSales(
            @RequestParam String period,
            @RequestParam int year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer quarter) {
        
        try {
            log.info("Exporting sales data: period={}, year={}, month={}, quarter={}", period, year, month, quarter);
            
            // Validate parameters
            if (ATTACHMENT.PeriodType.Month.equals(period) && month == null) {
                return ResponseEntity.badRequest().build();
            }
            if (ATTACHMENT.PeriodType.Quarter.equals(period) && quarter == null) {
                return ResponseEntity.badRequest().build();
            }
            
            // Generate Excel file
            byte[] excelData = attachmentService.export(period, year, month, quarter);
            
            // Create filename based on parameters
            String filename = "sales_";
            if (ATTACHMENT.PeriodType.Year.equals(period)) {
                filename += "year_" + year;
            } else if (ATTACHMENT.PeriodType.Quarter.equals(period)) {
                filename += "q" + quarter + "_" + year;
            } else if (ATTACHMENT.PeriodType.Month.equals(period)) {
                filename += "month_" + month + "_" + year;
            }
            filename += "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xlsx";
            
            // Set headers for file download
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", filename);
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelData);
            
        } catch (Exception e) {
            log.error("Error exporting sales data: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }
}
