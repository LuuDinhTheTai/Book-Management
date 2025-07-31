package com.me.book_management.service;

import com.me.book_management.entity.book.Book;
import com.me.book_management.entity.book.Category;

import java.util.List;

public interface CategoryService {

    Category create(Category category);
    List<Category> list();
    List<Book> listBooksByCategory(Long id);
    Category update(Category category);
    void delete(Long id);
}
