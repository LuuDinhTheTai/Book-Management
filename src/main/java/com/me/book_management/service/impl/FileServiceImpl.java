package com.me.book_management.service.impl;

import com.jlefebure.spring.boot.minio.MinioException;
import com.jlefebure.spring.boot.minio.MinioService;
import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.file.File;
import com.me.book_management.exception.BadRequestException;
import com.me.book_management.exception.NotFoundException;
import com.me.book_management.repository.account.AccountRepository;
import com.me.book_management.repository.FileRepository;
import com.me.book_management.service.FileService;
import com.me.book_management.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {

    private final MinioService minioService;
    private final FileRepository fileRepository;
    private final AccountRepository accountRepository;

    @Value("${spring.minio.bucket}")
    private String defaultBucket;

    @Value("${spring.minio.url}")
    private String minioUrl;

    @Override
    public File addFile(MultipartFile file, String fileType, Long accountId) {
        try {
            if (file.isEmpty()) {
                throw new BadRequestException("File cannot be empty");
            }

            Account account = accountRepository.findById(accountId)
                    .orElseThrow(() -> new NotFoundException("Account not found with id: " + accountId));

            String originalFileName = file.getOriginalFilename();
            String fileExtension = getFileExtension(originalFileName);
            String uniqueFileName = generateUniqueFileName(originalFileName);
            String objectKey = generateObjectKey(fileType, uniqueFileName);

            Path path = Path.of(objectKey);
            minioService.upload(path, file.getInputStream(), file.getContentType());
            
            log.info("File uploaded to MinIO: {} in bucket: {}", objectKey, defaultBucket);

            File fileEntity = File.builder()
                    .fileName(uniqueFileName)
                    .fileExtension(fileExtension)
                    .mimeType(file.getContentType())
                    .fileSize(file.getSize())
                    .bucketName(defaultBucket)
                    .objectKey(objectKey)
                    .fileUrl(generateFileUrl(objectKey))
                    .filePath(objectKey)
                    .fileType(fileType)
                    .isPublic(false)
                    .account(account)
                    .build();

            File savedFile = fileRepository.save(fileEntity);
            log.info("File saved to database with id: {}", savedFile.getId());
            
            return savedFile;

        } catch (IOException | MinioException ex) {
            log.error("Error uploading file: {}", ex.getMessage());
            throw new IllegalStateException("Failed to upload file: " + ex.getMessage());
        }
    }

    @Override
    public Optional<File> getFile(String fileName) {
        return fileRepository.findByFileName(fileName);
    }

    @Override
    public Optional<File> getFileById(Long id) {
        return fileRepository.findById(id);
    }

    @Override
    public File deleteFile(String fileName) {
        Optional<File> fileOpt = fileRepository.findByFileName(fileName);
        if (fileOpt.isEmpty()) {
            throw new NotFoundException("File not found with name: " + fileName);
        }

        File file = fileOpt.get();
        return deleteFileById(file.getId());
    }

    @Override
    public File deleteFileById(Long id) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("File not found with id: " + id));

        try {
            // Delete from MinIO
            Path path = Path.of(file.getObjectKey());
            minioService.remove(path);
            log.info("File removed from MinIO: {}", file.getObjectKey());

            // Delete from database
            fileRepository.delete(file);
            log.info("File deleted from database with id: {}", id);

            return file;
        } catch (MinioException ex) {
            log.error("Error deleting file from MinIO: {}", ex.getMessage());
            throw new IllegalStateException("Failed to delete file from MinIO: " + ex.getMessage());
        }
    }

    @Override
    public List<File> getFilesByAccountId(Long accountId) {
        return fileRepository.findByAccountId(accountId);
    }

    @Override
    public List<File> getFilesByBookId(Long bookId) {
        return fileRepository.findByBookId(bookId);
    }

    @Override
    public List<File> getFilesByType(String fileType) {
        return fileRepository.findByFileType(fileType);
    }

    @Override
    public String getFileUrl(String fileName) {
        Optional<File> fileOpt = fileRepository.findByFileName(fileName);
        if (fileOpt.isEmpty()) {
            throw new NotFoundException("File not found with name: " + fileName);
        }
        return fileOpt.get().getFileUrl();
    }

    @Override
    public String getFileUrlById(Long id) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("File not found with id: " + id));
        return file.getFileUrl();
    }

    @Override
    public boolean fileExists(String fileName) {
        return fileRepository.existsByFileName(fileName);
    }

    @Override
    public boolean fileExistsById(Long id) {
        return fileRepository.existsById(id);
    }

    // Helper methods
    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf('.') == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    private String generateUniqueFileName(String originalFileName) {
        String baseName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
        String extension = getFileExtension(originalFileName);
        String timestamp = String.valueOf(System.currentTimeMillis());
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return baseName + "_" + timestamp + "_" + uuid + extension;
    }

    private String generateObjectKey(String fileType, String fileName) {
        String dateFolder = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        return fileType.toLowerCase().replace(" ", "_") + "/" + dateFolder + "/" + fileName;
    }

    private String generateFileUrl(String objectKey) {
        return minioUrl + "/" + defaultBucket + "/" + objectKey;
    }
}