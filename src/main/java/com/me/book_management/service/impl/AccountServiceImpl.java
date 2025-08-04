package com.me.book_management.service.impl;

import com.me.book_management.dto.request.account.UpdateAccountRequest;
import com.me.book_management.dto.response.account.AccountResponse;
import com.me.book_management.entity.account.Account;
import com.me.book_management.exception.NotFoundException;
import com.me.book_management.repository.account.AccountRepository;
import com.me.book_management.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public AccountResponse find(Long id) {
        log.info("(find) account request: {}", id);

        AccountResponse accountResponse = AccountResponse
                .from(
                        accountRepository.findById(id)
                                .orElseThrow(() -> new NotFoundException("Account not found"))
                );

        log.info("(find) account response: {}", accountResponse);

        return accountResponse;
    }

    @Override
    public Account findByUsername(String username) {
        log.info("(find) account request: {}", username);

        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        log.info("(find) account response: {}", account);

        return account;
    }

    @Override
    public AccountResponse update(Long id, UpdateAccountRequest request) {
        log.info("(update) account: {}", request);

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Account not found"));
        AccountResponse response = AccountResponse.from(account);

        log.info("(update) account response: {}", response);

        return response;
    }

    @Override
    public void delete(Long id) {
        log.info("(delete) account: {}", id);

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Account not found"));
        account.setDeletedAt(LocalDateTime.now());
        account.setDeletedBy(account.getUsername());

        accountRepository.save(account);
    }

    @Override
    public long count() {
        log.info("(count) accounts request");
        
        long count = accountRepository.count();
        
        log.info("(count) accounts response: {}", count);
        
        return count;
    }
}
