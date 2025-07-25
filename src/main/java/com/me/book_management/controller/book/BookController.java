package com.me.book_management.controller.book;

import com.me.book_management.annotation.book.UpdateRequest;
import com.me.book_management.dto.request.book.CreateBookRequest;
import com.me.book_management.dto.request.book.CreateCommentRequest;
import com.me.book_management.dto.request.book.UpdateBookRequest;
import com.me.book_management.entity.book.Book;
import com.me.book_management.exception.InputException;
import com.me.book_management.service.BookService;
import com.me.book_management.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books/")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;
    private final CommentService commentService;

    @GetMapping("create")
    public String create(Model model) {
        CreateBookRequest createBookRequest = new CreateBookRequest();
        model.addAttribute("createBookRequest", createBookRequest);
        return "book/creation-form";
    }

    @PostMapping("create")
    public String create(@Valid @ModelAttribute("createBookRequest") CreateBookRequest request,
                         Model model) {
        try {
            request.validate();
            Book book = bookService.create(request);
            model.addAttribute("book", book);
            return "book/detail";

        } catch (InputException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "book/creation-form";
        }
    }

    @GetMapping("{id}")
    public String find(@PathVariable Long id,
                       @RequestParam(defaultValue = "0") int commentPage,
                       @RequestParam(defaultValue = "5") int commentSize,
                       Model model) {
        Book book = bookService.find(id);
        model.addAttribute("book", book);
        model.addAttribute("comments", commentService.findByBookId(id, PageRequest.of(commentPage, commentSize)));
        CreateCommentRequest commentRequest = new CreateCommentRequest();
        commentRequest.setBookId(id);
        model.addAttribute("createCommentRequest", commentRequest);
        return "book/detail";
    }

    @GetMapping("list")
    public String listBooks(@RequestParam(defaultValue = "0") int page, // TODO: change to dto
                            @RequestParam(defaultValue = "10") int size,
                            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookService.list(pageable);
        model.addAttribute("bookPage", bookPage);
        return "book/list";
    }

    @GetMapping("update/{id}")
    public String update(@PathVariable Long id,
                         Model model) {
        Book book = bookService.find(id);
        model.addAttribute("book", book);
        return "book/update-form";
    }

    @PostMapping("update")
    public String update(@Valid
                         @ModelAttribute("book")
                         @UpdateRequest
                         UpdateBookRequest request,
                         Model model) {
        try {
            request.validate();
            Book book = bookService.update(request);
            model.addAttribute("book", book);
            return "book/detail";

        } catch (InputException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "book/update-form";
        }
    }
}
