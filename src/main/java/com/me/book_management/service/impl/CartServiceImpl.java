package com.me.book_management.service.impl;

import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.book.Book;
import com.me.book_management.entity.cart.Cart;
import com.me.book_management.exception.NotFoundException;
import com.me.book_management.exception.InputException;
import com.me.book_management.repository.account.AccountRepository;
import com.me.book_management.repository.book.BookRepository;
import com.me.book_management.repository.cart.CartRepository;
import com.me.book_management.service.CartService;
import com.me.book_management.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final BookRepository bookRepository;
    private final AccountRepository accountRepository;

    @Override
    public Cart add(Long bookId) {
        log.info("(add) cart: {}", bookId);

        Account account = accountRepository.findByUsername(CommonUtil.getCurrentAccount())
                .orElseThrow(() -> new NotFoundException("Account not found"));
        
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found"));
        
        // Check if book is available
        if (book.getQty() <= 0) {
            throw new InputException("Book is out of stock");
        }
        
        // Check if the book already exists in the user's cart
        Optional<Cart> existingCart = cartRepository.findByAccountAndBook(account, book);
        if (existingCart.isPresent()) {
            // Book already in cart, increment quantity
            Cart cart = existingCart.get();
            int newQty = cart.getQty() + 1;
            
            // Check if adding one more would exceed available stock
            if (newQty > book.getQty()) {
                throw new InputException("Cannot add more items than available in stock");
            }
            
            cart.setQty(newQty);
            cart.setTotalPrice(book.getPrice() * cart.getQty());

            book.setQty(book.getQty() - 1);
            return cartRepository.save(cart);
        }
        
        // Book not in cart, create new cart item
        Cart newCart = new Cart();
        newCart.setBook(book);
        newCart.setAccount(account);
        newCart.setQty(1);
        newCart.setTotalPrice(book.getPrice());
        book.setQty(book.getQty() - 1);
        
        return cartRepository.save(newCart);
    }

    @Override
    public List<Cart> list() {
        Account account = accountRepository.findByUsername(CommonUtil.getCurrentAccount())
                .orElseThrow(() -> new NotFoundException("Account not found"));

        return cartRepository.findByAccount(account);
    }

    @Override
    public Cart update(Long id, Cart cart) {
        log.info("(update) cart: id={} {}", id, cart);
        Cart existingCart = cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cart not found"));
        
        // Check if the requested quantity exceeds available stock
        if (cart.getQty() > existingCart.getBook().getQty()) {
            throw new InputException("Cannot add more items than available in stock");
        }
        
        existingCart.setQty(cart.getQty());

        if (cart.getQty() == 0) {
            cartRepository.delete(existingCart);
            return null;
        }
        
        // Update total price based on new quantity
        existingCart.setTotalPrice(existingCart.getBook().getPrice() * cart.getQty());
        return cartRepository.save(existingCart);
    }

    @Override
    public void delete(Long id) {
        log.info("(delete) cart: {}", id);
        cartRepository.deleteById(id);
    }

    @Override
    public float getTotalPrice() {
        float totalPrice = 0;
        List<Cart> carts = list();
        for (Cart cart : carts) {
            totalPrice += cart.getTotalPrice();
        }
        return totalPrice;
    }
}
