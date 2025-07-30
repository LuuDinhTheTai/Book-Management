package com.me.book_management.exception.handler;

import com.me.book_management.exception.BadRequestException;
import com.me.book_management.exception.NotFoundException;
import com.me.book_management.exception.base.BaseException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public String handleBaseException(BaseException e, Model model) {
        log.error("Exception: {}", e.getMessage());
        model.addAttribute("status", e.getStatusCode());
//        model.addAttribute("message", e.getMessage());
        return "exception/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleInternalError(Exception e, Model model) {
        log.error("Internal Server Error: {}", e.getMessage());
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
//        model.addAttribute("message", e);
        return "exception/error";
    }
}
