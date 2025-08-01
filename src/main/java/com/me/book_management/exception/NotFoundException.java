package com.me.book_management.exception;

import com.me.book_management.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {

    public NotFoundException(String message) {
        super(message);
        super.statusCode = HttpStatus.NOT_FOUND.toString();
    }
}
