package com.me.book_management.controller.book;

import com.me.book_management.dto.request.book.comment.CreateCommentRequest;
import com.me.book_management.repository.book.CommentRepository;
import com.me.book_management.service.BookService;
import com.me.book_management.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/comments/")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final BookService bookService;

    private final CommentRepository commentRepository;

    @PostMapping("create")
    public String createComment(@Valid
                                @ModelAttribute("createCommentRequest")
                                CreateCommentRequest request,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // Redirect back to the book detail page with error
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid comment data");
            return "redirect:/books/" + request.getBookId();
        }

        try {
            request.validate();
            commentService.create(request);
            redirectAttributes.addFlashAttribute("successMessage", "Comment posted successfully!");
            return "redirect:/books/" + request.getBookId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/books/" + request.getBookId();
        }
    }
}
