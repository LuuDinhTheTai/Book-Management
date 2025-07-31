package com.me.book_management.controller.book;

import com.me.book_management.dto.request.book.comment.CreateCommentRequest;
import com.me.book_management.repository.book.CommentRepository;
import com.me.book_management.service.BookService;
import com.me.book_management.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
                                Model model) {
        request.validate();
        commentService.create(request);

        return "redirect:/books/" + request.getBookId();
    }
}
