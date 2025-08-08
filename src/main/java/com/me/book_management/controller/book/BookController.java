package com.me.book_management.controller.book;

import com.me.book_management.annotation.resourceOwner;
import com.me.book_management.constant.Constants;
import com.me.book_management.dto.request.book.CreateBookRequest;
import com.me.book_management.dto.request.book.ListBookRequest;
import com.me.book_management.dto.request.book.AdvancedSearchRequest;
import com.me.book_management.dto.request.book.BookDetailRequest;
import com.me.book_management.dto.request.book.comment.CreateCommentRequest;
import com.me.book_management.dto.request.book.UpdateBookRequest;
import com.me.book_management.entity.book.Book;
import com.me.book_management.exception.InputException;
import com.me.book_management.service.BookService;
import com.me.book_management.service.CartService;
import com.me.book_management.service.CategoryService;
import com.me.book_management.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/books/")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;
    private final CommentService commentService;
    private final CartService cartService;
    private final CategoryService categoryService;

    @GetMapping("create")
    public String create(Model model) {
        CreateBookRequest createBookRequest = new CreateBookRequest();
        model.addAttribute("createBookRequest", createBookRequest);
        model.addAttribute("statuses", Constants.BOOK_STATUS.list());
        model.addAttribute("languages", Constants.BOOK_LANGUAGE.list());
        model.addAttribute("formats", Constants.BOOK_FORMAT.list());
        model.addAttribute("categories", categoryService.list());

        return "book/creation-form";
    }

    @PostMapping("create")
    public String create(@Valid @ModelAttribute("createBookRequest")
                         CreateBookRequest request,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("statuses", Constants.BOOK_STATUS.list());
            model.addAttribute("languages", Constants.BOOK_LANGUAGE.list());
            model.addAttribute("formats", Constants.BOOK_FORMAT.list());
            model.addAttribute("categories", categoryService.list());
            return "book/creation-form";
        }
        try {
            Book book = bookService.create(request);
            redirectAttributes.addFlashAttribute("successMessage", "Book created successfully!");
            return "redirect:/books/" + book.getId();

        } catch (InputException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create book: " + e.getMessage());
            return "redirect:/books/create";
        } catch (Exception e) {
            log.error("Error creating book: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create book: " + e.getMessage());
            return "redirect:/books/create";
        }
    }

    @GetMapping("{id}")
    public String find(@PathVariable Long id,
                       @ModelAttribute BookDetailRequest request,
                       Model model) {
        model.addAttribute("book", bookService.find(id));
        model.addAttribute("createCommentRequest", new CreateCommentRequest());
        model.addAttribute("comments", commentService.findByBookId(id, request.getPageable()));
        model.addAttribute("carts", cartService.list());
        return "book/detail";
    }

    @GetMapping("list")
    public String list(@ModelAttribute ListBookRequest request,
                       Model model) {
        model.addAttribute("bookPage", bookService.list(request));
        return "book/list";
    }

    @GetMapping("update/{id}")
    public String update(@resourceOwner(instance = Constants.CLASSNAME.BOOK) @PathVariable Long id,
                         Model model) {
        Book book = bookService.find(id);
        model.addAttribute("book", book);
        model.addAttribute("statuses", Constants.BOOK_STATUS.list());
        model.addAttribute("languages", Constants.BOOK_LANGUAGE.list());
        model.addAttribute("formats", Constants.BOOK_FORMAT.list());
        model.addAttribute("categories", categoryService.list());
        return "book/update-form";
    }

    @PostMapping("update/{id}")
    public String update(@PathVariable @resourceOwner(instance = Constants.CLASSNAME.BOOK) Long id,
                         @Valid @ModelAttribute UpdateBookRequest request,
                         RedirectAttributes redirectAttributes) {
        try {
            Book book = bookService.update(id, request);
            redirectAttributes.addFlashAttribute("successMessage", "Book updated successfully!");
            return "redirect:/books/" + book.getId();

        } catch (InputException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update book: " + e.getMessage());
            return "redirect:/books/update/" + id;
        } catch (Exception e) {
            log.error("Error updating book: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update book: " + e.getMessage());
            return "redirect:/books/update/" + id;
        }
    }

    @PostMapping("delete/{id}")
    public String delete(@resourceOwner(instance = Constants.CLASSNAME.BOOK) @PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/accounts/profile";
    }

    @GetMapping("search")
    public String advancedSearch(@ModelAttribute AdvancedSearchRequest request, Model model) {
        model.addAttribute("searchRequest", request);
        model.addAttribute("bookPage", bookService.advancedSearch(request));
        model.addAttribute("statuses", Constants.BOOK_STATUS.list());
        model.addAttribute("languages", Constants.BOOK_LANGUAGE.list());
        model.addAttribute("formats", Constants.BOOK_FORMAT.list());
        model.addAttribute("categories", categoryService.list());
        return "search/advance-search";
    }
}
