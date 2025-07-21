package com.me.book_management.service.impl;

import com.me.book_management.entity.account.Account;
import com.me.book_management.repository.account.AccountRepository;
import com.me.book_management.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;


    @Override
    public Account findByUsername(String username) {
        log.info("(findByUsername) account: {}", username);
        return accountRepository.findByUsername(username);
    }
}
