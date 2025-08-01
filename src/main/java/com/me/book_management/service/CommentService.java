package com.me.book_management.service;

import com.me.book_management.dto.request.book.comment.CreateCommentRequest;
import com.me.book_management.dto.request.comment.ListCommentRequest;
import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.book.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    Comment create(CreateCommentRequest request);

    Page<Comment> findByBookId(Long bookId, Pageable pageable);

    List<Comment> list();

    Comment update(Comment comment);

    Comment find(Long id);

    List<Comment> findByAccount();

    Page<Comment> findByAccount(ListCommentRequest request);

    void delete(Long id);
}
