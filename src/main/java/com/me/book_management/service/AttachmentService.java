package com.me.book_management.service;

import com.me.book_management.dto.request.attachment.CreateAttachmentRequest;
import com.me.book_management.entity.file.File;

import java.util.List;

public interface AttachmentService {

    File upload(CreateAttachmentRequest request);

    File find(Long id);

    List<File> findByBookId(Long bookId);
    
    void delete(Long id);
} 