package com.me.book_management.service.impl;

import com.me.book_management.dto.request.cart.AddItemRequest;
import com.me.book_management.dto.request.cart.BuyRequest;
import com.me.book_management.dto.request.cart.DecreaseItemRequest;
import com.me.book_management.dto.request.cart.IncreaseItemRequest;
import com.me.book_management.dto.request.cart.ListCartRequest;
import com.me.book_management.constant.Constants;
import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.account.Address;
import com.me.book_management.entity.book.Book;
import com.me.book_management.entity.cart.Cart;
import com.me.book_management.entity.cart.CartBook;
import com.me.book_management.exception.BadRequestException;
import com.me.book_management.exception.NotFoundException;
import com.me.book_management.repository.account.AccountRepository;
import com.me.book_management.repository.account.AddressRepository;
import com.me.book_management.repository.book.BookRepository;
import com.me.book_management.repository.cart.CartBookRepository;
import com.me.book_management.repository.cart.CartRepository;
import com.me.book_management.service.CartService;
import com.me.book_management.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
    private final CartBookRepository cartBookRepository;
    private final AddressRepository addressRepository;

    @Override
    public Cart create() {
        log.info("(create) cart");
        Account account = accountRepository.findByUsername(CommonUtil.getCurrentAccount())
                .orElseThrow(() -> new NotFoundException("Account not found"));

        Cart cart = new Cart();
        cart.setAccount(account);

        return cartRepository.save(cart);
    }

    @Override
    public List<Cart> list() {
        Account account = accountRepository.findByUsername(CommonUtil.getCurrentAccount())
                .orElseThrow(() -> new NotFoundException("Account not found"));

        return cartRepository.findByAccount(account);
    }

    @Override
    public Page<Cart> list(ListCartRequest request) {
        log.info("(list) cart request: {}", request);

        Account account = accountRepository.findByUsername(CommonUtil.getCurrentAccount())
                .orElseThrow(() -> new NotFoundException("Account not found"));

        return cartRepository.findByAccountAndStatusNot(account, Constants.CART_STATUS.PENDING, request.getPageable());
    }

    @Override
    public void delete(Long id) {
        log.info("(delete) cart: {}", id);
        cartRepository.deleteById(id);
    }

    @Override
    public Cart addItem(AddItemRequest request) {
        log.info("(add) item: {}", request);
        Account account = accountRepository.findByUsername(CommonUtil.getCurrentAccount())
                .orElseThrow(() -> new NotFoundException("Account not found"));

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new NotFoundException("Book not found"));

        Optional<Cart> optionalCart = cartRepository.findByAccountAndId(account, request.getCartId());
        if (optionalCart.isEmpty()) {
            optionalCart = Optional.of(create());
        }
        Cart cart = optionalCart.get();

        // Check if book already exists in optionalCart
        Optional<CartBook> existingCartBook = cartBookRepository.findByCartAndBook(cart, book);

        if (existingCartBook.isPresent()) {
            // Update quantity
            CartBook cartBook = existingCartBook.get();
            int oldQty = cartBook.getQty();
            int newQty = oldQty + request.getQty();

            cartBook.setQty(newQty);
            cartBook.setPrice(book.getPrice() * newQty);
            cartBookRepository.save(cartBook);

            updateCartTotal(cart);
            return cartRepository.save(cart);
        }

        // Add new item
        CartBook cartBook = new CartBook();
        cartBook.setCart(cart);
        cartBook.setBook(book);
        cartBook.setQty(request.getQty());
        cartBook.setPrice(book.getPrice() * request.getQty());
        cartBookRepository.save(cartBook);

        updateCartTotal(cart);
        return cartRepository.save(cart);
    }

    @Override
    public Cart increaseItem(Long id, IncreaseItemRequest request) {
        CartBook cartBook = cartBookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cart item not found"));
        Cart cart = cartBook.getCart();

        int oldQty = cartBook.getQty();
        int newQty = oldQty + request.getQty();

        cartBook.setQty(newQty);
        cartBook.setPrice(cartBook.getBook().getPrice() * newQty);
        cartBookRepository.save(cartBook);

        updateCartTotal(cart);
        return cartRepository.save(cart);
    }

    @Override
    public Cart decreaseItem(Long id, DecreaseItemRequest request) {
        CartBook cartBook = cartBookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cart item not found"));
        Cart cart = cartBook.getCart();
        Book book = cartBook.getBook();

        int oldQty = cartBook.getQty();
        int newQty = oldQty - request.getQty();

        if (newQty <= 0) {
            // Remove item from cart
            cartBookRepository.deleteById(id);

            updateCartTotal(cart);
            return cartRepository.save(cart);
        }

        cartBook.setQty(newQty);
        cartBook.setPrice(book.getPrice() * newQty);
        cartBookRepository.save(cartBook);

        updateCartTotal(cart);
        return cartRepository.save(cart);
    }

    @Override
    public void buy(Long id) {
        log.info("(buy) request: {}", id);
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cart not found"));
        cart.setStatus(Constants.CART_STATUS.PROCESSING);
        cartRepository.save(cart);
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
