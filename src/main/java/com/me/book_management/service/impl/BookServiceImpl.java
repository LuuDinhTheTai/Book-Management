package com.me.book_management.service.impl;

import com.me.book_management.dto.request.book.CreateBookRequest;
import com.me.book_management.dto.request.book.UpdateBookRequest;
import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.book.Book;
import com.me.book_management.entity.book.Detail;
import com.me.book_management.exception.NotFoundException;
import com.me.book_management.repository.book.BookRepository;
import com.me.book_management.service.AccountService;
import com.me.book_management.service.BookService;
import com.me.book_management.service.DetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final DetailService detailService;
    private final AccountService accountService;

    @Override
    public Book create(CreateBookRequest request) {
        log.info("(create) request: {}", request);

        Book book = new Book();
        book.setName(request.getName());
        book.setPrice(request.getPrice());
        book.setQty(request.getQty());
        book.setStatus(request.getStatus());

        Detail detail = detailService.create(request.getDetail());
        book.setDetail(detail);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = accountService.findByUsername(authentication.getName());
        book.setAccount(account);

        return bookRepository.save(book);
    }

    @Override
    public Book find(Long id) {
        log.info("(find) id: {}", id);

        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found"));
    }

    @Override
    public Page<Book> list(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Book update(UpdateBookRequest request) {
        log.info("(update) request: {}", request);

        Book book = find(request.getId());

        if (request.getName() != null) {
            book.setName(request.getName());
        }

        book.setQty(request.getQty());
        if (request.getStatus() != null) {
            book.setStatus(request.getStatus());
        }
        if (request.getDetail() != null) {
            Detail detail = detailService.create(request.getDetail());
            book.setDetail(detail);
        }

        return bookRepository.save(book);
    }
}
