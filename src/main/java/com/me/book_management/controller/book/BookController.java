package com.me.book_management.controller.book;

import com.me.book_management.constant.Constants;
import com.me.book_management.dto.request.CreateBookRequest;
import com.me.book_management.entity.book.Book;
import com.me.book_management.service.BookService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.http.HttpRequest;

@Controller
@RequestMapping("/books/")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;

    @PreAuthorize("hasAuthority('CREATE_BOOK')")
    @GetMapping("create")
    public String create(Model model) {
        CreateBookRequest createBookRequest = new CreateBookRequest();
        model.addAttribute("createBookRequest", createBookRequest);
        model.addAttribute("createBookDetailRequest", createBookRequest.getDetail());
        return "create-book-form";
    }

    @PostMapping("create")
    public String create(@Valid @ModelAttribute("createBookRequest") CreateBookRequest request,
                         Model model) {
        try {
            request.validate();
            Book book = bookService.create(request);
            model.addAttribute("book", book);
            return "book-detail";

        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "create-book-form";
        }
    }
}
