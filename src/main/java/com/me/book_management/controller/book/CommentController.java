package com.me.book_management.controller.book;

import com.me.book_management.dto.request.CreateCommentRequest;
import com.me.book_management.entity.book.Comment;
import com.me.book_management.repository.book.CommentRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller("/comments/")
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;

    @PostMapping("create")
    public String createComment(@Valid
                                    @ModelAttribute("createCommentRequest")
                                CreateCommentRequest request,
                                Model model) {
        request.validate();

        return "redirect:/comments/list";
    }
}
