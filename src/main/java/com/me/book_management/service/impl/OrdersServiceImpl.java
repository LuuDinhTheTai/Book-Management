package com.me.book_management.service.impl;

import com.me.book_management.constant.Constants;
import com.me.book_management.dto.request.cart.ListOrdersRequest;
import com.me.book_management.entity.cart.Cart;
import com.me.book_management.repository.cart.CartRepository;
import com.me.book_management.service.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersServiceImpl implements OrdersService {
    
    private final CartRepository cartRepository;
    
    @Override
    public Page<Cart> list(ListOrdersRequest request) {
        log.info("(list) request: {}", request);
        Pageable pageable = request.getPageable();
        return cartRepository.findByStatusNot(Constants.CART_STATUS.PENDING, pageable);
    }
}
