package com.me.book_management.repository;

import com.me.book_management.entity.file.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    
    Optional<File> findByFileName(String fileName);
    
    List<File> findByAccountId(Long accountId);
    
    List<File> findByBookId(Long bookId);
    
    List<File> findByFileType(String fileType);
    
    @Query("SELECT f FROM File f WHERE f.account.id = :accountId AND f.fileType = :fileType")
    List<File> findByAccountIdAndFileType(@Param("accountId") Long accountId, @Param("fileType") String fileType);
    
    boolean existsByFileName(String fileName);
    
    boolean existsByObjectKey(String objectKey);
} 