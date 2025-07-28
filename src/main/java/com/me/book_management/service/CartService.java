package com.me.book_management.service;

import com.me.book_management.entity.book.Book;
import com.me.book_management.entity.cart.Cart;

import java.util.List;

public interface CartService {

    Cart add(Long bookId);
    List<Cart> list();
    Cart update(Long id, Cart cart);
    void delete(Long id);
    float getTotalPrice();
}
