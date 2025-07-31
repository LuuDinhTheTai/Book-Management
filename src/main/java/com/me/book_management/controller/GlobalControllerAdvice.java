package com.me.book_management.controller;

import com.me.book_management.entity.book.Category;
import com.me.book_management.exception.UnauthorizedAccessException;
import com.me.book_management.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final CategoryService categoryService;

    @ModelAttribute
    public void addCategoriesToModel(Model model) {
        try {
            List<Category> categories = categoryService.list();
            model.addAttribute("categories", categories);

        } catch (Exception e) {
            model.addAttribute("categories", List.of());
        }
    }
} 