package com.me.book_management.dto.request.attachment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAttachmentRequest {
    
    @NotNull(message = "File cannot be null")
    private MultipartFile file;
    
    private String fileType;

    private Long bookId;
    
    private Boolean isPublic = false;
}
