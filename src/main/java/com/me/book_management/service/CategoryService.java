package com.me.book_management.service;

import com.me.book_management.dto.request.book.category.CreateCategoryRequest;
import com.me.book_management.dto.request.book.category.UpdateCategoryRequest;
import com.me.book_management.entity.book.Book;
import com.me.book_management.entity.book.Category;

import java.util.List;

public interface CategoryService {

    Category create(CreateCategoryRequest request);

    Category find(Long id);

    List<Category> list();

    Category update(Long id, UpdateCategoryRequest request);

    void delete(Long id);
}
