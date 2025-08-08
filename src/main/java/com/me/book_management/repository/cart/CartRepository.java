package com.me.book_management.repository.cart;

import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.book.Book;
import com.me.book_management.entity.cart.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByAccount(Account account);
    @Query("select c from Cart c where c.account = :account and c.status = :status")
    List<Cart> findByAccountAndStatus(@Param("account") Account account, @Param("status") String status);

    @Query("select c from Cart c where c.account = :account and c.status = :status")
    Page<Cart> findByAccountAndStatus(@Param("account") Account account, @Param("status") String status, Pageable pageable);

    Optional<Cart> findByAccountAndId(Account account, Long id);

    @Query("select c from Cart c where c.account = :account and c.status <> :status")
    Page<Cart> findByAccountAndStatusNot(Account account, String status, Pageable pageable);
}
