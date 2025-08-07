package com.me.book_management.exception.handler;

import com.me.book_management.exception.BadRequestException;
import com.me.book_management.exception.ForbiddenException;
import com.me.book_management.exception.UnauthorizedException;
import com.me.book_management.exception.base.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.apache.tomcat.util.http.fileupload.impl.FileCountLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public String handleBaseException(BaseException e, Model model) {
        log.error("Exception: {}", e.getMessage());
        model.addAttribute("status", e.getStatusCode());
        model.addAttribute("message", e.getMessage());
        return "exception/error";
    }

    @ExceptionHandler(BadRequestException.class)
    public String handleBadRequestException(BadRequestException e, Model model) {
        log.error("Bad request exception: {} {}", e.getStatusCode(), e.getMessage());
        model.addAttribute("status", e.getStatusCode());
        model.addAttribute("message", e.getMessage());
        return "exception/error";
    }

    @ExceptionHandler(UnauthorizedException.class)
    public String handleUnauthorizedException(UnauthorizedException e, Model model) {
        log.error("Unauthorized exception: {} {}", e.getStatusCode(), e.getMessage());
        model.addAttribute("status", e.getStatusCode());
        model.addAttribute("message", e.getMessage());
        return "exception/error";
    }

    @ExceptionHandler(ForbiddenException.class)
    public String handleForbiddenException(ForbiddenException e, Model model) {
        log.error("Forbidden exception: {} {}", e.getStatusCode(), e.getMessage());
        model.addAttribute("status", e.getStatusCode());
        model.addAttribute("message", e.getMessage());
        return "exception/error";
    }

//    @ExceptionHandler(MultipartException.class)
    public String handleMultipartException(MultipartException e, Model model) {
        log.error("Multipart exception: {}", e.getMessage());
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("message", "File upload error: " + e.getMessage());
        return "exception/error";
    }
}
