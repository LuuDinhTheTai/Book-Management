package com.me.book_management.exception.handler;

import com.me.book_management.exception.BadRequestException;
import com.me.book_management.exception.NotFoundException;
import com.me.book_management.exception.UnauthorizedAccessException;
import com.me.book_management.exception.base.BaseException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(UnauthorizedAccessException.class)
    public String handleUnauthorizedAccessException(UnauthorizedAccessException e, Model model) {
        model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.toString());
        model.addAttribute("message", e.getMessage());
        return "exception/error";
    }
}
