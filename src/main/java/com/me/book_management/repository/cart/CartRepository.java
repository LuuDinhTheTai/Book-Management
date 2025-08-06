package com.me.book_management.repository.cart;

import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.book.Book;
import com.me.book_management.entity.cart.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByAccount(Account account);

    Page<Cart> findByAccountAndStatusNot(Account account, String status, Pageable pageable);

    Optional<Cart> findByAccountAndId(Account account, Long id);

    Page<Cart> findByStatusNot(String status, Pageable pageable);
}
