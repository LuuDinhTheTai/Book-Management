package com.me.book_management.dto.request.cart;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
public class ListOrdersRequest {

    private int page = 1;
    private int size = 10;

    public Pageable getPageable() {
        return PageRequest.of(this.page - 1, this.size);
    }
}
