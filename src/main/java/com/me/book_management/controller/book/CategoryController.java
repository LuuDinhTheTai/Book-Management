package com.me.book_management.controller.book;

import com.me.book_management.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories/")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("create")
    public String create() {

        return "category/create";
    }

    @PostMapping("create")
    public String create(Object o) {

        return "redirect:/books/list";
    }

    @GetMapping("list")
    public String list() {

        return "category/list";
    }
}
