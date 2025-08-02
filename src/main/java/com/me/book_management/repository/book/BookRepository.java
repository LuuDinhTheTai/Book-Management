package com.me.book_management.repository.book;

import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.book.Book;
import com.me.book_management.entity.book.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Override
    @Query("SELECT b FROM Book b WHERE b.deletedAt IS NULL AND b.deletedBy IS NULL")
    Page<Book> findAll(Pageable pageable);

    Page<Book> findByAccount(Account account,
                             Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE b.deletedAt IS NULL AND b.deletedBy IS NULL AND b.name LIKE %:name%")
    Page<Book> findByNameContaining(String name, Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE b.deletedAt IS NULL AND b.deletedBy IS NULL AND b.status = :status")
    Page<Book> findByStatus(String status, Pageable pageable);
    
    @Query("SELECT b FROM Book b JOIN b.categories c WHERE b.deletedAt IS NULL AND b.deletedBy IS NULL AND c.id = :categoryId")
    Page<Book> findByCategoryId(Long categoryId, Pageable pageable);
    
    @Query("SELECT b FROM Book b JOIN b.categories c WHERE b.deletedAt IS NULL AND b.deletedBy IS NULL AND c.id = :categoryId AND b.name LIKE %:name%")
    Page<Book> findByCategoryIdAndNameContaining(Long categoryId, String name, Pageable pageable);
    
    @Query("SELECT b FROM Book b JOIN b.categories c WHERE b.deletedAt IS NULL AND b.deletedBy IS NULL AND c.id = :categoryId AND b.status = :status")
    Page<Book> findByCategoryIdAndStatus(Long categoryId, String status, Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE b.deletedAt IS NULL AND b.deletedBy IS NULL AND b.name LIKE %:name% AND b.status = :status")
    Page<Book> findByNameContainingAndStatus(String name, String status, Pageable pageable);
}
