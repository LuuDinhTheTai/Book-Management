package com.me.book_management.service;

import com.me.book_management.dto.request.cart.AddItemRequest;
import com.me.book_management.dto.request.cart.DecreaseItemRequest;
import com.me.book_management.dto.request.cart.IncreaseItemRequest;
import com.me.book_management.dto.request.cart.ListCartRequest;
import com.me.book_management.entity.cart.Cart;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CartService {

    Cart create();

    Cart find(Long id);

    List<Cart> list();

    Page<Cart> list(ListCartRequest request);

    void delete(Long id);

    Cart addItem(AddItemRequest request);

    Cart increaseItem(Long id, IncreaseItemRequest request);

    Cart decreaseItem(Long id, DecreaseItemRequest request);
}
