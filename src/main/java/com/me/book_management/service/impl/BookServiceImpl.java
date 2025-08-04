package com.me.book_management.service.impl;

import com.me.book_management.dto.request.book.CreateBookRequest;
import com.me.book_management.dto.request.book.ListBookRequest;
import com.me.book_management.dto.request.book.AdvancedSearchRequest;
import com.me.book_management.dto.request.book.UpdateBookRequest;
import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.book.Book;
import com.me.book_management.entity.book.Category;
import com.me.book_management.entity.book.Detail;
import com.me.book_management.exception.NotFoundException;
import com.me.book_management.repository.CategoryRepository;
import com.me.book_management.repository.account.AccountRepository;
import com.me.book_management.repository.book.BookRepository;
import com.me.book_management.repository.book.DetailRepository;
import com.me.book_management.service.BookService;
import com.me.book_management.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AccountRepository accountRepository;
    private final DetailRepository detailRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Book create(CreateBookRequest request) {
        log.info("(create) request: {}", request);

        Book book = new Book();
        book.setName(request.getName());
        book.setPrice(request.getPrice());
        book.setQty(request.getQty());
        book.setStatus(request.getStatus());

        Detail detail = create(request.getDetail());
        book.setDetail(detail);

        Account account = accountRepository.findByUsername(CommonUtil.getCurrentAccount())
                .orElseThrow(() -> new NotFoundException("Account not found"));
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
    public Page<Book> list(ListBookRequest request) {
        log.info("(list) request: {}", request);
        
        Pageable pageable = request.getPageable();

        boolean searchMyBook = request.isMyBook();
        if (searchMyBook) {
            Account currentAccount = accountRepository.findByUsername(CommonUtil.getCurrentAccount())
                    .orElseThrow(() -> new NotFoundException("Account not found"));
            return bookRepository.findByAccount(currentAccount, pageable);
        }

        String name = request.getName();
        Long categoryId = request.getCategoryId();
        String status = request.getStatus();

        boolean findByCategory = categoryId != null;
        boolean findByName = name != null && !name.trim().isEmpty();
        boolean findByStatus = status != null && !status.trim().isEmpty();

        if (findByCategory && findByName) {
            return bookRepository.findByCategoryIdAndNameContaining(categoryId, name.trim(), pageable);
        }

        if (findByCategory && findByStatus) {
            return bookRepository.findByCategoryIdAndStatus(categoryId, status.trim(), pageable);
        }

        if (findByName && findByStatus) {
            return bookRepository.findByNameContainingAndStatus(name.trim(), status.trim(), pageable);
        }

        if (findByCategory) {
            return bookRepository.findByCategoryId(categoryId, pageable);
        }

        if (findByName) {
            return bookRepository.findByNameContaining(name.trim(), pageable);
        }

        if (findByStatus) {
            return bookRepository.findByStatus(status.trim(), pageable);
        }

        return bookRepository.findAll(pageable);
    }

    @Override
    public Book find(Long id) {
        log.info("(find) book: {}", id);

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found"));

        if (CommonUtil.isDeleted(book)) {
            throw new NotFoundException("Book not found");
        }

        return book;
    }

    @Override
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
    public void delete(Long id) {
        log.info("(delete) book: {}", id);

        Book book = find(id);
        book.setDeletedAt(LocalDateTime.now());
        book.setDeletedBy(CommonUtil.getCurrentAccount());

        bookRepository.save(book);
    }

    @Override
    public Page<Book> advancedSearch(AdvancedSearchRequest request) {
        log.info("(advancedSearch) request: {}", request);
        
        Pageable pageable = request.getPageable();
        
        // For now, we'll use the existing list method with basic filtering
        // In a real implementation, you would create custom repository methods
        // for more complex search criteria
        
        ListBookRequest listRequest = ListBookRequest.builder()
                .page(request.getPage())
                .size(request.getSize())
                .name(request.getName())
                .categoryId(request.getCategoryId())
                .status(request.getStatus())
                .build();
        
        return list(listRequest);
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
