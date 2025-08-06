package com.me.book_management.service;

import com.me.book_management.dto.request.cart.*;
import com.me.book_management.entity.cart.Cart;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CartService {

    Cart create();

    List<Cart> list();

    Page<Cart> list(ListCartRequest request);

    void delete(Long id);

    Cart addItem(AddItemRequest request);

    Cart increaseItem(Long id, IncreaseItemRequest request);

    Cart decreaseItem(Long id, DecreaseItemRequest request);

    void buy(Long id);
}
