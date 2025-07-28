package com.me.book_management.exception;

import com.me.book_management.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class InputException extends BaseException {

    public InputException(String message) {
        super(message);
        super.statusCode = HttpStatus.BAD_REQUEST.toString();
    }
}
