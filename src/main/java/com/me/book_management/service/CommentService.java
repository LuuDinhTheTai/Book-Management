package com.me.book_management.service;

import com.me.book_management.entity.book.Comment;

import java.util.List;

public interface CommentService {

    Comment create(Comment comment);
    List<Comment> findByBookId(Long bookId);
    List<Comment> list();
    Comment update(Comment comment);
    void delete(Long id);
}
