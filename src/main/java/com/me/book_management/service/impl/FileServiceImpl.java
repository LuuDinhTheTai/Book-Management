package com.me.book_management.service.impl;

import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.file.File;
import com.me.book_management.exception.NotFoundException;
import com.me.book_management.repository.account.AccountRepository;
import com.me.book_management.repository.file.FileRepository;
import com.me.book_management.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public File uploadFile(MultipartFile multipartFile, Long accountId, File.FileType fileType) {
        log.info("(uploadFile) accountId: {}, fileType: {}, originalFilename: {}", 
                accountId, fileType, multipartFile.getOriginalFilename());
        
        // Validate file
        if (!validateFile(multipartFile)) {
            throw new IllegalArgumentException("Invalid file");
        }
        
        // Get account
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found"));
        
        // Generate file info
        String originalFileName = multipartFile.getOriginalFilename();
        String fileExtension = getFileExtension(originalFileName);
        String fileName = generateFileName(originalFileName);
        String objectKey = generateObjectKey(originalFileName, accountId);
        String checksum = calculateChecksum(multipartFile);
        
        // Create file entity
        File file = new File();
        file.setFileName(fileName);
        file.setOriginalFileName(originalFileName);
        file.setFileExtension(fileExtension);
        file.setMimeType(multipartFile.getContentType());
        file.setFileSize(multipartFile.getSize());
        file.setBucketName("book-management"); // Default bucket
        file.setObjectKey(objectKey);
        file.setFileType(fileType);
        file.setFileStatus(File.FileStatus.UPLOADING);
        file.setAccount(account);
        file.setChecksum(checksum);
        file.setUploadDate(LocalDateTime.now());
        
        // TODO: Upload to MinIO
        // MinIO upload logic will be implemented here
        
        // Update status to uploaded
        file.setFileStatus(File.FileStatus.UPLOADED);
        
        File savedFile = fileRepository.save(file);
        log.info("(uploadFile) file uploaded successfully: {}", savedFile.getId());
        return savedFile;
    }

    @Override
    public File find(Long id) {
        log.info("(find) file: {}", id);
        
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("File not found"));
        
        log.info("(find) file response: {}", file);
        return file;
    }

    @Override
    public List<File> findByAccountId(Long accountId) {
        log.info("(findByAccountId) accountId: {}", accountId);
        
        List<File> files = fileRepository.findByAccountId(accountId);
        log.info("(findByAccountId) files size: {}", files.size());
        return files;
    }

    @Override
    public Page<File> findByAccountId(Long accountId, Pageable pageable) {
        log.info("(findByAccountId) accountId: {}, pageable: {}", accountId, pageable);
        
        Page<File> files = fileRepository.findByAccountId(accountId, pageable);
        log.info("(findByAccountId) files size: {}", files.getTotalElements());
        return files;
    }

    @Override
    public List<File> findByAccountIdAndFileType(Long accountId, File.FileType fileType) {
        log.info("(findByAccountIdAndFileType) accountId: {}, fileType: {}", accountId, fileType);
        
        List<File> files = fileRepository.findByAccountIdAndFileType(accountId, fileType);
        log.info("(findByAccountIdAndFileType) files size: {}", files.size());
        return files;
    }

    @Override
    public List<File> findByAccountIdAndFileStatus(Long accountId, File.FileStatus fileStatus) {
        log.info("(findByAccountIdAndFileStatus) accountId: {}, fileStatus: {}", accountId, fileStatus);
        
        List<File> files = fileRepository.findByAccountIdAndFileStatus(accountId, fileStatus);
        log.info("(findByAccountIdAndFileStatus) files size: {}", files.size());
        return files;
    }

    @Override
    public Optional<File> findByObjectKey(String objectKey) {
        log.info("(findByObjectKey) objectKey: {}", objectKey);
        
        Optional<File> file = fileRepository.findByObjectKey(objectKey);
        log.info("(findByObjectKey) file found: {}", file.isPresent());
        return file;
    }

    @Override
    public Optional<File> findByFileNameAndAccountId(String fileName, Long accountId) {
        log.info("(findByFileNameAndAccountId) fileName: {}, accountId: {}", fileName, accountId);
        
        Optional<File> file = fileRepository.findByFileNameAndAccountId(fileName, accountId);
        log.info("(findByFileNameAndAccountId) file found: {}", file.isPresent());
        return file;
    }

    @Override
    public List<File> findByOriginalFileNameAndAccountId(String originalFileName, Long accountId) {
        log.info("(findByOriginalFileNameAndAccountId) originalFileName: {}, accountId: {}", originalFileName, accountId);
        
        List<File> files = fileRepository.findByOriginalFileNameAndAccountId(originalFileName, accountId);
        log.info("(findByOriginalFileNameAndAccountId) files size: {}", files.size());
        return files;
    }

    @Override
    public List<File> findByBucketName(String bucketName) {
        log.info("(findByBucketName) bucketName: {}", bucketName);
        
        List<File> files = fileRepository.findByBucketName(bucketName);
        log.info("(findByBucketName) files size: {}", files.size());
        return files;
    }

    @Override
    public List<File> findPublicFiles() {
        log.info("(findPublicFiles)");
        
        List<File> files = fileRepository.findPublicFiles();
        log.info("(findPublicFiles) files size: {}", files.size());
        return files;
    }

    @Override
    public List<File> findDeletedFiles() {
        log.info("(findDeletedFiles)");
        
//        List<File> files = fileRepository.findDeletedFiles();
//        log.info("(findDeletedFiles) files size: {}", files.size());
        return null;
    }

    @Override
    public List<File> findByUploadDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("(findByUploadDateBetween) startDate: {}, endDate: {}", startDate, endDate);
        
        List<File> files = fileRepository.findByUploadDateBetween(startDate, endDate);
        log.info("(findByUploadDateBetween) files size: {}", files.size());
        return files;
    }

    @Override
    public List<File> findByFileSizeGreaterThan(Long minSize) {
        log.info("(findByFileSizeGreaterThan) minSize: {}", minSize);
        
        List<File> files = fileRepository.findByFileSizeGreaterThan(minSize);
        log.info("(findByFileSizeGreaterThan) files size: {}", files.size());
        return files;
    }

    @Override
    public List<File> findByMimeTypeContaining(String mimeType) {
        log.info("(findByMimeTypeContaining) mimeType: {}", mimeType);
        
        List<File> files = fileRepository.findByMimeTypeContaining(mimeType);
        log.info("(findByMimeTypeContaining) files size: {}", files.size());
        return files;
    }

    @Override
    public Optional<File> findByChecksum(String checksum) {
        log.info("(findByChecksum) checksum: {}", checksum);
        
        Optional<File> file = fileRepository.findByChecksum(checksum);
        log.info("(findByChecksum) file found: {}", file.isPresent());
        return file;
    }

    @Override
    public Long countByAccountId(Long accountId) {
        log.info("(countByAccountId) accountId: {}", accountId);
        
        Long count = fileRepository.countByAccountId(accountId);
        log.info("(countByAccountId) count: {}", count);
        return count;
    }

    @Override
    public Long getTotalFileSizeByAccountId(Long accountId) {
        log.info("(getTotalFileSizeByAccountId) accountId: {}", accountId);
        
        Long totalSize = fileRepository.getTotalFileSizeByAccountId(accountId);
        log.info("(getTotalFileSizeByAccountId) totalSize: {}", totalSize);
        return totalSize != null ? totalSize : 0L;
    }

    @Override
    public List<File> findRecentFilesByAccountId(Long accountId, Pageable pageable) {
        log.info("(findRecentFilesByAccountId) accountId: {}, pageable: {}", accountId, pageable);
        
        List<File> files = fileRepository.findRecentFilesByAccountId(accountId, pageable);
        log.info("(findRecentFilesByAccountId) files size: {}", files.size());
        return files;
    }

    @Override
    @Transactional
    public File update(Long id, File file) {
        log.info("(update) id: {}, file: {}", id, file);
        
        File existingFile = fileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("File not found"));
        
        // Update fields
        existingFile.setDescription(file.getDescription());
        existingFile.setIsPublic(file.getIsPublic());
        
        File updatedFile = fileRepository.save(existingFile);
        log.info("(update) file response: {}", updatedFile);
        return updatedFile;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("(delete) file: {}", id);
        
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("File not found"));
        
        // TODO: Delete from MinIO
        // MinIO delete logic will be implemented here
        
        fileRepository.delete(file);
        log.info("(delete) file deleted successfully");
    }

    @Override
    @Transactional
    public void softDelete(Long id) {
        log.info("(softDelete) file: {}", id);
        
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("File not found"));
        
//        file.setIsDeleted(true);
        fileRepository.save(file);
        log.info("(softDelete) file soft deleted successfully");
    }

    @Override
    @Transactional
    public void restore(Long id) {
        log.info("(restore) file: {}", id);
        
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("File not found"));
        
//        file.setIsDeleted(false);
        fileRepository.save(file);
        log.info("(restore) file restored successfully");
    }

    @Override
    @Transactional
    public void updateFileStatus(Long id, File.FileStatus status) {
        log.info("(updateFileStatus) id: {}, status: {}", id, status);
        
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("File not found"));
        
        file.setFileStatus(status);
        fileRepository.save(file);
        log.info("(updateFileStatus) file status updated successfully");
    }

    @Override
    @Transactional
    public void incrementAccessCount(Long id) {
        log.info("(incrementAccessCount) file: {}", id);
        
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("File not found"));
        
        file.setAccessCount(file.getAccessCount() + 1);
        file.setLastAccessed(LocalDateTime.now());
        fileRepository.save(file);
        log.info("(incrementAccessCount) access count incremented successfully");
    }

    @Override
    @Transactional
    public void updateLastAccessed(Long id) {
        log.info("(updateLastAccessed) file: {}", id);
        
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("File not found"));
        
        file.setLastAccessed(LocalDateTime.now());
        fileRepository.save(file);
        log.info("(updateLastAccessed) last accessed updated successfully");
    }

    @Override
    public String generatePresignedUrl(Long id, int expirationMinutes) {
        log.info("(generatePresignedUrl) id: {}, expirationMinutes: {}", id, expirationMinutes);
        
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("File not found"));
        
        // TODO: Generate MinIO presigned URL
        // MinIO presigned URL generation will be implemented here
        
        String presignedUrl = "https://minio.example.com/" + file.getBucketName() + "/" + file.getObjectKey();
        log.info("(generatePresignedUrl) presignedUrl: {}", presignedUrl);
        return presignedUrl;
    }

    @Override
    public boolean validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }
        
        // Check file size (max 100MB)
        if (file.getSize() > 100 * 1024 * 1024) {
            return false;
        }
        
        // Check file extension
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            return false;
        }
        
        String extension = getFileExtension(originalFilename).toLowerCase();
        String[] allowedExtensions = {".pdf", ".epub", ".mobi", ".txt", ".doc", ".docx", ".jpg", ".jpeg", ".png", ".gif"};
        
        for (String allowedExt : allowedExtensions) {
            if (extension.equals(allowedExt)) {
                return true;
            }
        }
        
        return false;
    }

    @Override
    public String calculateChecksum(MultipartFile file) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(file.getBytes());
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException | IOException e) {
            log.error("Error calculating checksum", e);
            return null;
        }
    }

    @Override
    public String generateObjectKey(String originalFileName, Long accountId) {
        String extension = getFileExtension(originalFileName);
        String uuid = UUID.randomUUID().toString();
        return "users/" + accountId + "/" + uuid + extension;
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

    private String generateFileName(String originalFileName) {
        String extension = getFileExtension(originalFileName);
        return UUID.randomUUID().toString() + extension;
    }
} 