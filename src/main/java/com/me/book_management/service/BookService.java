package com.me.book_management.service;

import com.me.book_management.dto.request.CreateBookRequest;
import com.me.book_management.entity.book.Book;

public interface BookService {

    Book create(CreateBookRequest request);
}
