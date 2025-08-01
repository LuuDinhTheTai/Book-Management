package com.me.book_management.service;

import com.me.book_management.entity.file.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface FileService {

    File uploadFile(MultipartFile multipartFile, Long accountId, File.FileType fileType);

    File find(Long id);

    List<File> findByAccountId(Long accountId);

    Page<File> findByAccountId(Long accountId, Pageable pageable);

    List<File> findByAccountIdAndFileType(Long accountId, File.FileType fileType);

    List<File> findByAccountIdAndFileStatus(Long accountId, File.FileStatus fileStatus);

    Optional<File> findByObjectKey(String objectKey);

    Optional<File> findByFileNameAndAccountId(String fileName, Long accountId);

    List<File> findByOriginalFileNameAndAccountId(String originalFileName, Long accountId);

    List<File> findByBucketName(String bucketName);

    List<File> findPublicFiles();

    List<File> findDeletedFiles();

    List<File> findByUploadDateBetween(java.time.LocalDateTime startDate, java.time.LocalDateTime endDate);

    List<File> findByFileSizeGreaterThan(Long minSize);

    List<File> findByMimeTypeContaining(String mimeType);

    Optional<File> findByChecksum(String checksum);

    Long countByAccountId(Long accountId);

    Long getTotalFileSizeByAccountId(Long accountId);

    List<File> findRecentFilesByAccountId(Long accountId, Pageable pageable);

    File update(Long id, File file);

    void delete(Long id);

    void softDelete(Long id);

    void restore(Long id);

    void updateFileStatus(Long id, File.FileStatus status);

    void incrementAccessCount(Long id);

    void updateLastAccessed(Long id);

    String generatePresignedUrl(Long id, int expirationMinutes);

    boolean validateFile(MultipartFile file);

    String calculateChecksum(MultipartFile file);

    String generateObjectKey(String originalFileName, Long accountId);
} 