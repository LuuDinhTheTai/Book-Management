package com.me.book_management.service;

import com.me.book_management.dto.request.cart.ListOrdersRequest;
import com.me.book_management.entity.cart.Cart;
import org.springframework.data.domain.Page;

public interface OrdersService {

    Page<Cart> list(ListOrdersRequest request);
}
