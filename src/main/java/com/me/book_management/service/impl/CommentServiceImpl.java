package com.me.book_management.service.impl;

import com.me.book_management.annotation.comment.Create;
import com.me.book_management.dto.request.book.comment.CreateCommentRequest;
import com.me.book_management.entity.book.Comment;
import com.me.book_management.repository.book.CommentRepository;
import com.me.book_management.service.AccountService;
import com.me.book_management.service.BookService;
import com.me.book_management.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookService bookService;
    private final AccountService accountService;

    @Override
    @Create
    public Comment create(CreateCommentRequest request) {
        log.info("(create) comment: {}", request);

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setBook(bookService.find(request.getBookId()));
        comment.setAccount(accountService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));

        return commentRepository.save(comment);
    }

    @Override
    public Page<Comment> findByBookId(Long bookId, Pageable pageable) {
        log.info("(find) comment: book {}", bookId);
        return commentRepository.findByBookId(bookId, pageable);
    }

    @Override
    public List<Comment> list() {
        return List.of();
    }

    @Override
    public Comment update(Comment comment) {
        log.info("(update) comment: {}", comment);

        return null;
    }

    @Override
    public Comment find(Long id) {
        log.info("(find) comment id: {}", id);
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    @Override
    public void delete(Long id) {
        log.info("(delete) comment id: {}", id);

    }
}
