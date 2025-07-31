package com.me.book_management.dto.request.cart;

public abstract class UpdateItemRequest {

    abstract public Long getCartBookId();
    abstract public int getQty();
}
