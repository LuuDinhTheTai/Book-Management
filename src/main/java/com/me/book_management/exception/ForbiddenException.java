package com.me.book_management.exception;

import com.me.book_management.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends BaseException {

    public ForbiddenException(String message) {
        super(message);
        super.statusCode = HttpStatus.FORBIDDEN.toString();
    }
}
