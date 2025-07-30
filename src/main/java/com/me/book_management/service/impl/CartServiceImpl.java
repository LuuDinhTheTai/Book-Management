package com.me.book_management.service.impl;

import com.me.book_management.annotation.cart.Create;
import com.me.book_management.annotation.cart.Delete;
import com.me.book_management.annotation.cart.Read;
import com.me.book_management.annotation.cart.Update;
import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.book.Book;
import com.me.book_management.entity.cart.Cart;
import com.me.book_management.entity.cart.CartBook;
import com.me.book_management.exception.NotFoundException;
import com.me.book_management.repository.account.AccountRepository;
import com.me.book_management.repository.book.BookRepository;
import com.me.book_management.repository.cart.CartBookRepository;
import com.me.book_management.repository.cart.CartRepository;
import com.me.book_management.service.CartService;
import com.me.book_management.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final BookRepository bookRepository;
    private final AccountRepository accountRepository;
    private final CartBookRepository cartBookRepository;

    @Override
    @Create
    public Cart add(Long bookId) {
        log.info("(add to cart) book: {}", bookId);

        Account account = accountRepository.findByUsername(CommonUtil.getCurrentAccount())
                .orElseThrow(() -> new NotFoundException("Account not found"));

        Cart cart = getOrCreateCart(account);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found"));

        CartBook existingCartBook = cartBookRepository.findByCartAndBook(cart, book)
                .orElse(null);

        if (!CommonUtil.isNull(existingCartBook)) {
            existingCartBook.setQty(existingCartBook.getQty() + 1);
            existingCartBook.setPrice(book.getPrice() * existingCartBook.getQty());
            cartBookRepository.save(existingCartBook);

        } else {
            CartBook newCartBook = new CartBook();
            newCartBook.setCart(cart);
            newCartBook.setBook(book);
            newCartBook.setQty(1);
            newCartBook.setPrice(book.getPrice());
            cartBookRepository.save(newCartBook);
        }

        updateCartTotalPrice(cart);

        return cart;
    }

    @Override
    @Read
    public Cart get() {
        Account account = accountRepository.findByUsername(CommonUtil.getCurrentAccount())
                .orElseThrow(() -> new NotFoundException("Account not found"));

        return getOrCreateCart(account);
    }

    @Override
    @Update
    public Cart update(Long id, int qty) {
        log.info("(update) cart: {}, qty={}", id, qty);

        Cart cart = get();
        CartBook cartBook = cartBookRepository.findByCartAndId(cart, id)
                .orElseThrow(() -> new NotFoundException("Cart item not found"));

        cartBook.setQty(qty);
        cartBook.setPrice(cartBook.getBook().getPrice() * qty);
        cartBookRepository.save(cartBook);
        
        updateCartTotalPrice(cart);
        return cart;
    }

    @Override
    @Delete
    public void delete(Long id) {
        log.info("(delete) cart: {}", id);

        Cart cart = get();
        CartBook cartBook = cartBookRepository.findByCartAndId(cart, id)
                .orElseThrow(() -> new NotFoundException("Cart item not found"));
        
        cartBookRepository.delete(cartBook);
        updateCartTotalPrice(cart);
    }

    private Cart getOrCreateCart(Account account) {
        if (account.getCart() == null) {
            Cart newCart = new Cart();
            newCart.setAccount(account);
            newCart.setTotalPrice(0.0f);
            cartRepository.save(newCart);
            account.setCart(newCart);
            accountRepository.save(account);
            return newCart;
        }
        return account.getCart();
    }

    private void updateCartTotalPrice(Cart cart) {
        List<CartBook> cartBooks = cartBookRepository.findByCart(cart);
        float totalPrice = 0.0f;
        for (CartBook cartBook : cartBooks) {
            totalPrice += cartBook.getPrice();
        }
        cart.setTotalPrice(totalPrice);
        cartRepository.save(cart);
    }
}
