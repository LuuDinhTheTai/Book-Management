package com.me.book_management.exception;

import com.me.book_management.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {

    public BadRequestException(String message) {
        super(message);
        super.statusCode = HttpStatus.BAD_REQUEST.toString();
    }
}
