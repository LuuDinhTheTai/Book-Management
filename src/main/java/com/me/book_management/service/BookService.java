package com.me.book_management.service;

import com.me.book_management.dto.request.book.CreateBookRequest;
import com.me.book_management.dto.request.book.DeleteBookRequest;
import com.me.book_management.dto.request.book.UpdateBookRequest;
import com.me.book_management.entity.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    Book create(CreateBookRequest request);

    Book find(Long id);

    Page<Book> list(Pageable pageable);

    Book update(UpdateBookRequest request);

    Page<Book> findByAccount(Pageable pageable);

    void delete(Long id);
}
