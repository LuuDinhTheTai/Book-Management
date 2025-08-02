package com.me.book_management.dto.request.book;

import com.me.book_management.constant.Constants;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ListBookRequest {

    private int page;
    private int size;
    private String name;
    private Long categoryId;
    private String status = Constants.BOOK_STATUS.AVAILABLE;
    private boolean myBook = false;

    public Pageable getPageable() {
        int validPage = Math.max(1, page);
        int validSize = Math.max(10, size);
        return PageRequest.of(validPage - 1, validSize);
    }
}
