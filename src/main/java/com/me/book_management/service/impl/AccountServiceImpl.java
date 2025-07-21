package com.me.book_management.service.impl;

import com.me.book_management.entity.account.Account;
import com.me.book_management.repository.account.AccountRepository;
import com.me.book_management.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;


    @Override
    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }
}
