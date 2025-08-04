package com.me.book_management.service;

import com.me.book_management.dto.request.book.CreateBookRequest;
import com.me.book_management.dto.request.book.DeleteBookRequest;
import com.me.book_management.dto.request.book.ListBookRequest;
import com.me.book_management.dto.request.book.AdvancedSearchRequest;
import com.me.book_management.dto.request.book.UpdateBookRequest;
import com.me.book_management.entity.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    Book create(CreateBookRequest request);

    Page<Book> list(ListBookRequest request);

    Book find(Long id);

    Book update(Long id, UpdateBookRequest request);

    void delete(Long id);

    Page<Book> advancedSearch(AdvancedSearchRequest request);
}
