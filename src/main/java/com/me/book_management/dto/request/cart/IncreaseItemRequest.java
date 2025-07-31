package com.me.book_management.dto.request.cart;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class IncreaseItemRequest extends UpdateItemRequest {

    private Long cartBookId;
    private final int qty = 1;
}
