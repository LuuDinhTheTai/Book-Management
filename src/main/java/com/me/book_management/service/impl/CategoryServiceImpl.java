package com.me.book_management.service.impl;

import com.me.book_management.annotation.category.Create;
import com.me.book_management.annotation.category.Delete;
import com.me.book_management.annotation.category.Update;
import com.me.book_management.dto.request.book.category.CreateCategoryRequest;
import com.me.book_management.dto.request.book.category.UpdateCategoryRequest;
import com.me.book_management.entity.book.Book;
import com.me.book_management.entity.book.Category;
import com.me.book_management.exception.NotFoundException;
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
    @Create
    public Category create(CreateCategoryRequest request) {
        log.info("(create) category: {}", request);

        if (categoryRepository.existsByName(request.getName())) {
            log.error("Category with name '{}' already exists", request.getName());
            throw new IllegalArgumentException("Category with this name already exists");
        }

        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        return categoryRepository.save(category);
    }

    @Override
    public Category find(Long id) {
        log.info("(find) category: {}", id);
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }

    @Override
    public List<Category> list() {
        return categoryRepository.findAll();
    }

    @Override
    @Update
    public Category update(Long id, UpdateCategoryRequest request) {
        log.info("(update) category: {}", request);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        return categoryRepository.save(category);
    }

    @Override
    @Delete
    public void delete(Long id) {
        log.info("(delete) category id: {}", id);
        categoryRepository.deleteById(id);
    }
}
