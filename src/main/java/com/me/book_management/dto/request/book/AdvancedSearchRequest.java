package com.me.book_management.dto.request.book;

import com.me.book_management.constant.Constants;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AdvancedSearchRequest {

    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int size = 10;
    private String name;
    private String author;
    private String description;
    private Long categoryId;
    private String status;
    private String language;
    private String format;
    private Double minPrice;
    private Double maxPrice;
    @Builder.Default
    private String sortBy = "name";
    @Builder.Default
    private String sortDirection = "asc";

    public Pageable getPageable() {
        int validPage = Math.max(1, page);
        int validSize = Math.max(10, size);
        return PageRequest.of(validPage - 1, validSize);
    }

    public boolean hasFilters() {
        return name != null && !name.trim().isEmpty() ||
               author != null && !author.trim().isEmpty() ||
               description != null && !description.trim().isEmpty() ||
               categoryId != null ||
               status != null && !status.trim().isEmpty() ||
               language != null && !language.trim().isEmpty() ||
               format != null && !format.trim().isEmpty() ||
               minPrice != null ||
               maxPrice != null;
    }
} 