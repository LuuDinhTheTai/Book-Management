package com.me.book_management.service.impl;

import com.me.book_management.dto.request.SignInRequest;
import com.me.book_management.dto.request.SignUpRequest;
import com.me.book_management.dto.response.AccountResponse;
import com.me.book_management.entity.account.Account;
import com.me.book_management.repository.account.AccountRepository;
import com.me.book_management.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(SignUpRequest request) {
        log.info("(signUp) request: {}", request);
        Account account = new Account();
        account.setEmail(request.getEmail());
        account.setUsername(request.getUsername());
        account.setPassword(passwordEncoder.encode(request.getPassword()));

    }

    @Override
    public AccountResponse login(SignInRequest request) {
        return null;
    }
}
