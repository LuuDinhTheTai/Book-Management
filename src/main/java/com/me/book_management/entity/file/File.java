package com.me.book_management.entity.file;

import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.base.EntityWithUpdater;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "files")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"account"})
@Builder
public class File extends EntityWithUpdater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileExtension;

    @Column(nullable = false)
    private String mimeType;

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false)
    @Builder.Default
    private String bucketName = "data";

    @Column(nullable = false)
    private String objectKey;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_type")
    @Builder.Default
    private String fileType = FileType.BookCover;

    @Column(name = "is_public")
    @Builder.Default
    private Boolean isPublic = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private com.me.book_management.entity.book.Book book;

    public static class FileType {
        public static final String BookCover = "Book Cover";
        public static final String BookPdf = "Book Pdf";

        public static List<String> list() {
            return List.of(BookCover, BookPdf);
        }
    }
} 