package com.me.book_management.service.impl;

import com.me.book_management.annotation.book.Create;
import com.me.book_management.annotation.book.Delete;
import com.me.book_management.annotation.book.Update;
import com.me.book_management.dto.request.book.CreateBookRequest;
import com.me.book_management.dto.request.book.DeleteBookRequest;
import com.me.book_management.dto.request.book.UpdateBookRequest;
import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.book.Book;
import com.me.book_management.entity.book.Category;
import com.me.book_management.entity.book.Detail;
import com.me.book_management.exception.NotFoundException;
import com.me.book_management.repository.CategoryRepository;
import com.me.book_management.repository.book.BookRepository;
import com.me.book_management.repository.book.DetailRepository;
import com.me.book_management.service.AccountService;
import com.me.book_management.service.BookService;
import com.me.book_management.service.CategoryService;
import com.me.book_management.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AccountService accountService;
    private final DetailRepository detailRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Create
    public Book create(CreateBookRequest request) {
        log.info("(create) request: {}", request);

        Book book = new Book();
        book.setName(request.getName());
        book.setPrice(request.getPrice());
        book.setQty(request.getQty());
        book.setStatus(request.getStatus());

        Detail detail = create(request.getDetail());
        book.setDetail(detail);

        Account account = accountService.findByUsername(CommonUtil.getCurrentAccount());
        book.setAccount(account);

        boolean hasCategoriesParam = request.getCategories() != null && !request.getCategories().isEmpty();
        if (hasCategoriesParam) {
            for (Long categoryId : request.getCategories()) {
                Optional<Category> category = categoryRepository.findById(categoryId);
                if (category.isEmpty()) {
                    continue;
                }
                book.getCategories().add(category.get());
            }
        }

        bookRepository.save(book);

        log.info("(create) book response: {}", book);

        return book;
    }

    @Override
    public Book find(Long id) {
        log.info("(find) id: {}", id);

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found"));

        log.info("(find) book response: {}", book);

        return book;
    }

    @Override
    public List<Book> findByCategory(Long id) {
        log.info("(find) category: {}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));

        return category.getBooks().stream().toList();
    }

    @Override
    public Page<Book> list(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    @Update
    public Book update(Long id, UpdateBookRequest request) {
        log.info("(update) request: {}", request);

        Book book = find(id);

        UpdateBookRequest.toBook(request, book);

        boolean hasCategoriesParam = request.getCategories() != null && !request.getCategories().isEmpty();
        if (hasCategoriesParam) {
            for (Long categoryId : request.getCategories()) {
                Optional<Category> category = categoryRepository.findById(categoryId);
                if (category.isEmpty()) {
                    continue;
                }
                book.getCategories().add(category.get());
            }

        }

        return bookRepository.save(book);
    }

    @Override
    public Page<Book> findByAccount(Pageable pageable) {
        Account account = accountService.findByUsername(CommonUtil.getCurrentAccount());

        return bookRepository.findByAccount(account, pageable);
    }

    @Override
    @Delete
    public void delete(Long id) {
        log.info("(delete) book: {}", id);

        Book book = find(id);
        book.setDeletedAt(LocalDateTime.now());
        book.setDeletedBy(CommonUtil.getCurrentAccount());

        bookRepository.save(book);
    }

    private Detail create(CreateBookRequest.Detail detail) {
        log.info("(create) detail: {}", detail);

        Detail detailEntity = new Detail();
        detailEntity.setIsbn(detail.getIsbn());
        detailEntity.setTitle(detail.getTitle());
        detailEntity.setSubtitle(detail.getSubtitle());
        detailEntity.setAuthor(detail.getAuthor());
        detailEntity.setPublisher(detail.getPublisher());
        detailEntity.setPublishedDate(detail.getPublishedDate());
        detailEntity.setDescription(detail.getDescription());
        detailEntity.setPageCount(detail.getPageCount());
        detailEntity.setLanguage(detail.getLanguage());
        detailEntity.setFormat(detail.getFormat());

        detailRepository.save(detailEntity);

        log.info("(create) detail response: {}", detailEntity);
        return detailEntity;
    }
}
