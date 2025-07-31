package com.me.book_management.service.impl;

import com.me.book_management.entity.book.Book;
import com.me.book_management.entity.book.Category;
import com.me.book_management.repository.CategoryRepository;
import com.me.book_management.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category create(Category category) {
        log.info("(create) category: {}", category);
        return null;
    }

    @Override
    public List<Category> list() {
        return List.of();
    }

    @Override
    public List<Book> listBooksByCategory(Long id) {
        log.info("(list) category: {}", id);
        return List.of();
    }

    @Override
    public Category update(Category category) {
        log.info("(update) category: {}", category);
        return null;
    }

    @Override
    public void delete(Long id) {
        log.info("(delete) category: {}", id);

    }
}
