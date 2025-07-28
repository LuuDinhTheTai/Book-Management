package com.me.book_management.exception;

import com.me.book_management.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseException {

    public UnauthorizedException(String message) {
        super(message);
        super.statusCode = HttpStatus.UNAUTHORIZED.toString();
    }
}
