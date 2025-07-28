package com.me.book_management.exception.base;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    protected String statusCode;

    public BaseException(String message) {
        super(message);
    }

}
