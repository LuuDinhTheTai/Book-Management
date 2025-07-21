package com.me.book_management.service;

import com.me.book_management.dto.request.ForgotPasswordRequest;
import com.me.book_management.dto.request.SignInRequest;
import com.me.book_management.dto.request.SignUpRequest;
import com.me.book_management.dto.response.AccountResponse;
import com.me.book_management.entity.account.Account;
import jakarta.validation.Valid;

public interface AuthService {

    void signUp(SignUpRequest request);
    Account signIn(SignInRequest request);
    void resetPassword(ForgotPasswordRequest request);
}
