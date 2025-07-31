package com.me.book_management.service.impl;

import com.me.book_management.annotation.cart.Create;
import com.me.book_management.annotation.cart.Delete;
import com.me.book_management.annotation.cart.Read;
import com.me.book_management.dto.request.cart.AddItemRequest;
import com.me.book_management.dto.request.cart.UpdateItemRequest;
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
import com.me.book_management.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public Cart create() {
        log.info("(create) cart");
        String currentUsername = SecurityUtil.getCurrentAccount();
        Account account = accountRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        Cart cart = new Cart();
        cart.setAccount(account);
        cart.setTotalPrice(0.0f);
        
        return cartRepository.save(cart);
    }

    @Override
    @Read
    public Cart find(Long id) {
        log.info("(find) cart: {}", id);
        return cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cart not found"));
    }

    @Override
    @Read
    public List<Cart> list() {
        String currentUsername = SecurityUtil.getCurrentAccount();
        Account account = accountRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new NotFoundException("Account not found"));
        
        return cartRepository.findByAccount(account);
    }

    @Override
    @Delete
    public void delete(Long id) {
        log.info("(delete) cart: {}", id);
        cartRepository.deleteById(id);
    }

    @Override
    public Cart addItem(AddItemRequest request) {
        log.info("(add) item: {}", request);
        Account account = accountRepository.findByUsername(SecurityUtil.getCurrentAccount())
                .orElseThrow(() -> new NotFoundException("Account not found"));

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new NotFoundException("Book not found"));
        Cart cart = cartRepository.findByAccountAndId(account, request.getCartId())
                .orElseThrow(() -> new NotFoundException("Cart not found"));

        // Check if book already exists in cart
        Optional<CartBook> existingCartBook = cartBookRepository.findByCartAndBook(cart, book);
        
        if (existingCartBook.isPresent()) {
            // Update quantity
            CartBook cartBook = existingCartBook.get();
            cartBook.setQty(cartBook.getQty() + request.getQty());
            cartBook.setPrice(book.getPrice() * cartBook.getQty());
            cartBookRepository.save(cartBook);
        } else {
            // Add new item
            CartBook cartBook = new CartBook();
            cartBook.setCart(cart);
            cartBook.setBook(book);
            cartBook.setQty(request.getQty());
            cartBook.setPrice(book.getPrice() * request.getQty());
            cartBookRepository.save(cartBook);
        }

        updateCartTotal(cart);
        
        return cart;
    }

    @Override
    public Cart updateItem(UpdateItemRequest request) {
        CartBook cartBook = cartBookRepository.findById(request.getCartBookId())
                .orElseThrow(() -> new NotFoundException("Cart item not found"));

        if (request.getQty() <= 0) {
            cartBookRepository.delete(cartBook);

        } else {
            cartBook.setQty(request.getQty());
            cartBook.setPrice(cartBook.getBook().getPrice() * request.getQty());
            cartBookRepository.save(cartBook);
        }

        updateCartTotal(cartBook.getCart());
        
        return cartBook.getCart();
    }

    private void updateCartTotal(Cart cart) {
        List<CartBook> cartBooks = cartBookRepository.findByCart(cart);
        float total = 0.0f;
        for (CartBook cartBook : cartBooks) {
            total += cartBook.getPrice();
        }
        cart.setTotalPrice(total);
        cartRepository.save(cart);
    }
}
