package com.me.book_management.repository.book;

import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.book.Book;
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
}
