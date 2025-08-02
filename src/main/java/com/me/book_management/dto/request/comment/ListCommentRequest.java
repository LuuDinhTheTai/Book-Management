package com.me.book_management.dto.request.comment;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ListCommentRequest {

    private int page;
    private int size;

    public Pageable getPageable() {
        int validPage = Math.max(1, page);
        int validSize = Math.max(10, size);
        return PageRequest.of(validPage - 1, validSize);
    }
} 