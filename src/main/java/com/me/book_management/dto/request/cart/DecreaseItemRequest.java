package com.me.book_management.dto.request.cart;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DecreaseItemRequest extends UpdateItemRequest {

    private Long cartBookId;
    private int qty = 1;
}
