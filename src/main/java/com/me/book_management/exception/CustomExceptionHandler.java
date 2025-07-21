package com.me.book_management.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
  
  @ExceptionHandler(CustomException.class)
  public String handleCustomException(CustomException e, Model model) {
    model.addAttribute("message", e.getMessage());
    return "error";
  }
}
