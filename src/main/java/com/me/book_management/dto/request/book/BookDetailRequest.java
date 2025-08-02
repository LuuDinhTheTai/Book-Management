package com.me.book_management.dto.request.book;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BookDetailRequest {

    private int commentPage = 1;
    private int commentSize = 10;

    public Pageable getPageable() {
        int validPage = Math.max(1, commentPage);
        int validSize = Math.max(10, commentSize);
        return PageRequest.of(validPage - 1, validSize);
    }
}
