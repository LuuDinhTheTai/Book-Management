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
        return AccountResponse.from(accountRepository.findById(id).orElseThrow(() -> new NotFoundException("Account not found")));
    }

    @Override
    public Account findByUsername(String username) {
        log.info("(find) account: {}", username);

        return accountRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Account not found"));
    }

    @Override
    public AccountResponse update(Long id, UpdateAccountRequest request) {
        log.info("(update) request: {}", request);

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        return AccountResponse.from(accountRepository.save(account));
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
}
