package com.me.book_management.service;

import com.me.book_management.entity.cart.Cart;

public interface CartService {

    Cart add(Long bookId);

    Cart get();

    Cart update(Long id, int qty);

    void delete(Long id);
}
