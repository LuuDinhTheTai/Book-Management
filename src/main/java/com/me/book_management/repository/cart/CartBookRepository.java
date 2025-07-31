package com.me.book_management.repository.cart;

import com.me.book_management.entity.book.Book;
import com.me.book_management.entity.cart.Cart;
import com.me.book_management.entity.cart.CartBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartBookRepository extends JpaRepository<CartBook, Long> {

    Optional<CartBook> findByCartAndBook(Cart cart, Book book);
    
    List<CartBook> findByCart(Cart cart);
    
    Optional<CartBook> findByCartAndId(Cart cart, Long id);
    
    void deleteByCart(Cart cart);
}
