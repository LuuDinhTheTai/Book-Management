package com.me.book_management.exception.handler;

import com.me.book_management.exception.BadRequestException;
import com.me.book_management.exception.NotFoundException;
import com.me.book_management.exception.base.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public String handleBaseException(BaseException e, Model model) {
        model.addAttribute("status", e.getStatusCode());
//        model.addAttribute("message", e.getMessage());
        return "exception/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleInternalError(Exception e, Model model) {
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
//        model.addAttribute("message", e);
        return "exception/error";
    }
}
