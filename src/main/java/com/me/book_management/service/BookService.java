package com.me.book_management.service;

import com.me.book_management.dto.request.CreateBookRequest;
import com.me.book_management.entity.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    Book create(CreateBookRequest request);
    Book find(Long id);
    Page<Book> list(Pageable pageable);
}
