package com.me.book_management.service;

import com.me.book_management.entity.file.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface FileService {

    File addFile(MultipartFile file, String fileType, Long accountId);
    Optional<File> getFile(String fileName);
    Optional<File> getFileById(Long id);
    File deleteFile(String fileName);
    File deleteFileById(Long id);
    List<File> getFilesByAccountId(Long accountId);
    List<File> getFilesByBookId(Long bookId);
    List<File> getFilesByType(String fileType);
    String getFileUrl(String fileName);
    String getFileUrlById(Long id);
    boolean fileExists(String fileName);
    boolean fileExistsById(Long id);
} 