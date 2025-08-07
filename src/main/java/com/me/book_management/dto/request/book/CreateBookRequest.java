package com.me.book_management.dto.request.book;

import com.me.book_management.entity.book.Detail;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateBookRequest {

    @NotBlank(message = "Name cannot be blank")
    private String name;
    @Positive(message = "Price must be positive")
    private float price;
    @PositiveOrZero(message = "Qty must be positive or zero")
    private int qty;
    @NotBlank(message = "Status cannot be blank")
    private String status;
    private Set<Long> categories;
    
    // File upload fields
    private MultipartFile coverImageFile;
    private MultipartFile bookFile;
    
    @Valid
    private Detail detail = new Detail();

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class Detail {

        @NotBlank(message = "ISBN cannot be blank")
        private String isbn;
        @NotBlank(message = "Title cannot be blank")
        @Size(max = 30, message = "Title cannot be more than 30 characters")
        private String title;
        @NotBlank(message = "Subtitle cannot be blank")
        @Size(max = 100, message = "Subtitle cannot be more than 100 characters")
        private String subtitle;
        @NotBlank(message = "Author cannot be blank")
        private String author;
        @NotBlank(message = "Publisher cannot be blank")
        private String publisher;
        @NotNull(message = "Published date cannot be null")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime publishedDate;
        @NotBlank(message = "Description cannot be blank")
        @Size(max = 1000, message = "Description cannot be more than 1000 characters")
        private String description;
        @Positive(message = "Page count must be positive")
        private String pageCount;
        @NotBlank(message = "Language cannot be blank")
        private String language;
        @NotBlank(message = "Format cannot be blank")
        private String format;

        public static void toDetail(
                Detail resource,
                com.me.book_management.entity.book.Detail target) {
            target.setIsbn(resource.getIsbn());
            target.setTitle(resource.getTitle());
            target.setSubtitle(resource.getSubtitle());
            target.setAuthor(resource.getAuthor());
            target.setPublisher(resource.getPublisher());
            target.setPublishedDate(resource.getPublishedDate());
            target.setDescription(resource.getDescription());
            target.setPageCount(resource.getPageCount());
            target.setLanguage(resource.getLanguage());
            target.setFormat(resource.getFormat());
        }
    }
}
