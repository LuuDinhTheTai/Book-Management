package com.me.book_management.service.impl;

import com.me.book_management.entity.book.Comment;
import com.me.book_management.repository.book.CommentRepository;
import com.me.book_management.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Comment create(Comment comment) {
        log.info("(create) comment: {}", comment);

        return null;
    }

    @Override
    public List<Comment> findByBookId(Long bookId) {
        log.info("(find) comment by book id: {}", bookId);
        return List.of();
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
    public void delete(Long id) {
        log.info("(delete) comment id: {}", id);

    }
}
