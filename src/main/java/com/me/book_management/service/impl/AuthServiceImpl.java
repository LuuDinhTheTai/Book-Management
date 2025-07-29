package com.me.book_management.service.impl;

import com.me.book_management.constant.Constants;
import com.me.book_management.dto.request.auth.ForgotPasswordRequest;
import com.me.book_management.dto.request.auth.SignInRequest;
import com.me.book_management.dto.request.auth.SignUpRequest;
import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.rbac0.Role;
import com.me.book_management.exception.InputException;
import com.me.book_management.repository.account.AccountRepository;
import com.me.book_management.service.AuthService;
import com.me.book_management.service.RoleService;
import com.me.book_management.util.CookieUtil;
import com.me.book_management.util.JwtUtil;
import jakarta.servlet.http.Cookie;
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
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;

    @Override
    public void signUp(SignUpRequest request) {
        log.info("(signUp) request: {}", request);

        if (accountRepository.existsByEmail(request.getEmail())) {
            throw new InputException("Email already exists");
        }
        if (accountRepository.existsByUsername(request.getUsername())) {
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
    public Cookie signIn(SignInRequest request) {
        log.info("(signIn) request: {}", request);

        Account account = accountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InputException("Username not found"));

        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new InputException("Invalid password");
        }

        String token = jwtUtil.generateToken(account);
        Cookie tokenCookie = cookieUtil.create(Constants.COOKIE.ACCESS_TOKEN, token);

        return tokenCookie;
    }

    @Override
    public void resetPassword(ForgotPasswordRequest request) {
        log.info("(resetPassword) request: {}", request);

//        Account account = accountRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new InputException("Email not found"));
//
//        if (!account.getUsername().equals(request.getUsername())) {
//            throw new InputException("Username not found for this email");
//        }
//
//        account.setPassword(passwordEncoder.encode(request.getPassword()));
//        accountRepository.save(account);
    }
}
