package com.me.book_management.repository.book;

import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.book.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByBookId(Long bookId, Pageable pageable);

    List<Comment> findByAccount(Account account);
}
