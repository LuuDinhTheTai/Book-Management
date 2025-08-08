package com.me.book_management.service.impl;

import com.me.book_management.constant.Constants;
import com.me.book_management.dto.request.cart.ListOrdersRequest;
import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.cart.Cart;
import com.me.book_management.exception.NotFoundException;
import com.me.book_management.repository.account.AccountRepository;
import com.me.book_management.repository.cart.CartRepository;
import com.me.book_management.service.OrdersService;
import com.me.book_management.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersServiceImpl implements OrdersService {

    private final AccountRepository accountRepository;
    private final CartRepository cartRepository;
    
    @Override
    public Page<Cart> list(ListOrdersRequest request) {
        log.info("(list) request: {}", request);

        Account account = accountRepository.findByUsername(CommonUtil.getCurrentAccount())
                .orElseThrow(() -> new NotFoundException("Account not found"));
        Pageable pageable = request.getPageable();
        return cartRepository.findByAccountAndStatusNot(account, Constants.CART_STATUS.PENDING, pageable);
    }
}
