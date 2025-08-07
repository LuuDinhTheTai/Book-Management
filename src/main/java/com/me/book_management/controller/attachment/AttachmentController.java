package com.me.book_management.controller.attachment;

import com.me.book_management.dto.request.attachment.CreateAttachmentRequest;
import com.me.book_management.entity.file.File;
import com.me.book_management.service.AttachmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
}
