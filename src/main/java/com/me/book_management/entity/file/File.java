package com.me.book_management.entity.file;

import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.base.EntityWithUpdater;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "files")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"account"})
public class File extends EntityWithUpdater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String originalFileName;

    @Column(nullable = false)
    private String fileExtension;

    @Column(nullable = false)
    private String mimeType;

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false)
    private String bucketName;

    @Column(nullable = false)
    private String objectKey;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "file_path")
    private String filePath;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type")
    private FileType fileType = FileType.BOOK;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_status")
    private FileStatus fileStatus = FileStatus.UPLOADED;

    @Column(name = "upload_date")
    private LocalDateTime uploadDate = LocalDateTime.now();

    @Column(name = "last_accessed")
    private LocalDateTime lastAccessed;

    @Column(name = "access_count")
    private Long accessCount = 0L;

    @Column(name = "is_public")
    private Boolean isPublic = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "checksum")
    private String checksum;

    @Column(name = "description")
    private String description;

    public enum FileType {
        BOOK,
        BOOK_COVER,
        BOOK_PDF,
        BOOK_EPUB,
        BOOK_MOBI,
        USER_AVATAR,
        OTHER
    }

    public enum FileStatus {
        UPLOADING,
        UPLOADED,
        PROCESSING,
        PROCESSED,
        FAILED,
        DELETED
    }
} 