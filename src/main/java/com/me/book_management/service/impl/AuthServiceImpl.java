package com.me.book_management.service.impl;

import com.me.book_management.constant.Constants;
import com.me.book_management.dto.request.ForgotPasswordRequest;
import com.me.book_management.dto.request.SignInRequest;
import com.me.book_management.dto.request.SignUpRequest;
import com.me.book_management.dto.response.AccountResponse;
import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.rbac0.Role;
import com.me.book_management.exception.CustomException;
import com.me.book_management.exception.InputException;
import com.me.book_management.exception.NotFoundException;
import com.me.book_management.repository.account.AccountRepository;
import com.me.book_management.service.AuthService;
import com.me.book_management.service.RoleService;
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
    private final RoleService roleService;

    @Override
    public void signUp(SignUpRequest request) {
        log.info("(signUp) request: {}", request);

        if (existedByEmail(request.getEmail())) {
            throw new InputException("Email already exists");
        }
        if (existedByUsername(request.getUsername())) {
            throw new InputException("Username already exists");
        }

        Account account = new Account();
        account.setEmail(request.getEmail());
        account.setUsername(request.getUsername());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        Role role = roleService.findByName(Constants.ROLE.USER);

        if (role == null) {
            throw new InputException("Role not found");
        }

        account.getRoles().add(role);
        accountRepository.save(account);
    }

    @Override
    public Account signIn(SignInRequest request) {
        log.info("(signIn) request: {}", request);

        Account account = accountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InputException("Username not found"));

        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new InputException("Invalid password");
        }

        return account;
    }

    @Override
    public void resetPassword(ForgotPasswordRequest request) {
        log.info("(resetPassword) request: {}", request);

        Account account = accountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InputException("Email not found"));

        if (!account.getUsername().equals(request.getUsername())) {
            throw new InputException("Username not found for this email");
        }

        account.setPassword(passwordEncoder.encode(request.getPassword()));
        accountRepository.save(account);
    }

    private boolean existedByEmail(String email) {
        if (accountRepository.findByEmail(email) != null) {
            return true;
        }
        return false;
    }

    private boolean existedByUsername(String username) {
        if (accountRepository.findByUsername(username) != null) {
            return true;
        }
        return false;
    }
}
