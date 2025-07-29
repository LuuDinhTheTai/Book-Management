package com.me.book_management.service;

import com.me.book_management.dto.request.auth.ForgotPasswordRequest;
import com.me.book_management.dto.request.auth.SignInRequest;
import com.me.book_management.dto.request.auth.SignUpRequest;
import com.me.book_management.entity.account.Account;
import jakarta.servlet.http.Cookie;

public interface AuthService {

    void signUp(SignUpRequest request);
    Cookie signIn(SignInRequest request);
    void resetPassword(ForgotPasswordRequest request);
}
