package com.me.book_management.configuration.security.impl;

import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.rbac0.Role;
import com.me.book_management.repository.account.AccountRepository;
import com.me.book_management.service.RoleService;
import com.me.book_management.service.impl.RoleServiceImpl;
import com.me.book_management.util.CookieUtil;
import com.me.book_management.util.JwtUtil;
import com.me.book_management.constant.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
public class OAuth2AuthenticationSuccessHandlerImpl extends SimpleUrlAuthenticationSuccessHandler {

    private final AccountRepository accountRepository;
    private final RoleService roleService;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;

    public OAuth2AuthenticationSuccessHandlerImpl(AccountRepository accountRepository,
                                                  RoleService roleService,
                                                  JwtUtil jwtUtil,
                                                  CookieUtil cookieUtil) {
        super("/books/list");
        this.accountRepository = accountRepository;
        this.roleService = roleService;
        this.jwtUtil = jwtUtil;
        this.cookieUtil = cookieUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("(onAuthenticationSuccess) authentication: {}", authentication);

        if (authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
            Map<String, Object> attributes = oauth2User.getAttributes();
            String email = (String) attributes.get("email");

            log.info("(onAuthenticationSuccess) email: {}", email);

            // Tìm account theo email
            Account account = accountRepository.findByEmail(email).orElse(null);

            if (account == null) {
                account = findOrCreateAccount(email, (String) attributes.get("name"));
            }

            log.info("(onAuthenticationSuccess) Found account: {}", account.getUsername());

            // Tạo JWT token
            String token = jwtUtil.generateToken(account);
            log.info("(onAuthenticationSuccess) Generated token for user: {}", account.getUsername());

            // Tạo cookie
            Cookie cookie = cookieUtil.create(Constants.COOKIE.ACCESS_TOKEN, token);
            response.addCookie(cookie);

            log.info("(onAuthenticationSuccess) Added token cookie for user: {}", account.getUsername());
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private Account findOrCreateAccount(String email, String name) {
        log.info("(findOrCreateAccount) email: {}, name: {}", email, name);

        // Tìm account theo email
        Account account = accountRepository.findByEmail(email).orElse(null);

        if (account == null) {
            log.info("(findOrCreateAccount) Creating new account for email: {}", email);

            // Tạo account mới
            account = new Account();
            account.setEmail(email);
            account.setUsername(email); // Sử dụng email làm username
            account.setPassword(""); // OAuth2 user không có password

            // Thêm role USER mặc định
            Role userRole = roleService.findByName(Constants.ROLE.USER);
            if (userRole != null) {
                account.getRoles().add(userRole);
            }

            // Lưu account
            account = accountRepository.save(account);
            log.info("(findOrCreateAccount) Created account with id: {}", account.getId());

        } else {
            log.info("(findOrCreateAccount) Found existing account with id: {}", account.getId());
        }

        return account;
    }
} 