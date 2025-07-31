package com.me.book_management.dto.request.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddItemRequest {

    private Long cartId;
    private Long bookId;
    private Integer qty = 1;
}
