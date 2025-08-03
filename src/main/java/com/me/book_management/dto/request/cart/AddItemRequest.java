package com.me.book_management.dto.request.cart;

import com.me.book_management.annotation.resourceOwner;
import com.me.book_management.constant.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddItemRequest {

    @resourceOwner(instance = Constants.CLASSNAME.CART)
    private Long cartId;
    private Long bookId;
    private Integer qty = 1;
}
