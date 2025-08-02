package com.me.book_management.service.impl;

import com.me.book_management.annotation.comment.Create;
import com.me.book_management.dto.request.book.comment.CreateCommentRequest;
import com.me.book_management.dto.request.comment.ListCommentRequest;
import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.book.Comment;
import com.me.book_management.exception.NotFoundException;
import com.me.book_management.repository.account.AccountRepository;
import com.me.book_management.repository.book.BookRepository;
import com.me.book_management.repository.book.CommentRepository;
import com.me.book_management.service.AccountService;
import com.me.book_management.service.BookService;
import com.me.book_management.service.CommentService;
import com.me.book_management.util.CommonUtil;
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
    private final BookRepository bookRepository;
    private final AccountRepository accountRepository;

    @Override
    @Create
    public Comment create(CreateCommentRequest request) {
        log.info("(create) comment: {}", request);

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setBook(bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new NotFoundException("Book not found with id: " + request.getBookId()))
        );

        Account account = accountRepository.findByUsername(CommonUtil.getCurrentAccount())
                        .orElseThrow(() -> new NotFoundException("Account not found"));
        comment.setAccount(account);

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
    public List<Comment> findByAccount() {
        Account account = accountRepository.findByUsername(CommonUtil.getCurrentAccount())
                .orElseThrow(() -> new NotFoundException("Account not found"));
        return commentRepository.findByAccount(account);
    }

    @Override
    public Page<Comment> findByAccount(ListCommentRequest request) {
        log.info("(findByAccount) comment request: {}", request);
        
        Account account = accountRepository.findByUsername(CommonUtil.getCurrentAccount())
                .orElseThrow(() -> new NotFoundException("Account not found"));
        return commentRepository.findByAccount(account, request.getPageable());
    }

    @Override
    public void delete(Long id) {
        log.info("(delete) comment id: {}", id);

    }
}
