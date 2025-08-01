package com.me.book_management.repository.file;

import com.me.book_management.entity.file.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    @Query("SELECT f FROM File f WHERE f.account.id = :accountId")
    List<File> findByAccountId(@Param("accountId") Long accountId);

    @Query("SELECT f FROM File f WHERE f.account.id = :accountId")
    Page<File> findByAccountId(@Param("accountId") Long accountId, Pageable pageable);

    @Query("SELECT f FROM File f WHERE f.account.id = :accountId AND f.fileType = :fileType")
    List<File> findByAccountIdAndFileType(@Param("accountId") Long accountId, @Param("fileType") File.FileType fileType);

    @Query("SELECT f FROM File f WHERE f.account.id = :accountId AND f.fileStatus = :fileStatus")
    List<File> findByAccountIdAndFileStatus(@Param("accountId") Long accountId, @Param("fileStatus") File.FileStatus fileStatus);

    @Query("SELECT f FROM File f WHERE f.objectKey = :objectKey")
    Optional<File> findByObjectKey(@Param("objectKey") String objectKey);

    @Query("SELECT f FROM File f WHERE f.fileName = :fileName AND f.account.id = :accountId")
    Optional<File> findByFileNameAndAccountId(@Param("fileName") String fileName, @Param("accountId") Long accountId);

    @Query("SELECT f FROM File f WHERE f.originalFileName = :originalFileName AND f.account.id = :accountId")
    List<File> findByOriginalFileNameAndAccountId(@Param("originalFileName") String originalFileName, @Param("accountId") Long accountId);

    @Query("SELECT f FROM File f WHERE f.bucketName = :bucketName")
    List<File> findByBucketName(@Param("bucketName") String bucketName);

    @Query("SELECT f FROM File f WHERE f.isPublic = true")
    List<File> findPublicFiles();

//    @Query("SELECT f FROM File f WHERE f.isDeleted = true")
//    List<File> findDeletedFiles();

    @Query("SELECT f FROM File f WHERE f.uploadDate >= :startDate AND f.uploadDate <= :endDate")
    List<File> findByUploadDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT f FROM File f WHERE f.fileSize > :minSize")
    List<File> findByFileSizeGreaterThan(@Param("minSize") Long minSize);

    @Query("SELECT f FROM File f WHERE f.mimeType LIKE %:mimeType%")
    List<File> findByMimeTypeContaining(@Param("mimeType") String mimeType);

    @Query("SELECT f FROM File f WHERE f.checksum = :checksum")
    Optional<File> findByChecksum(@Param("checksum") String checksum);

    @Query("SELECT COUNT(f) FROM File f WHERE f.account.id = :accountId")
    Long countByAccountId(@Param("accountId") Long accountId);

    @Query("SELECT SUM(f.fileSize) FROM File f WHERE f.account.id = :accountId")
    Long getTotalFileSizeByAccountId(@Param("accountId") Long accountId);

    @Query("SELECT f FROM File f WHERE f.account.id = :accountId ORDER BY f.uploadDate DESC")
    List<File> findRecentFilesByAccountId(@Param("accountId") Long accountId, Pageable pageable);
} 