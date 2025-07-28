package com.me.book_management.dto.response.account;

import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.book.Book;
import com.me.book_management.entity.cart.Cart;
import com.me.book_management.entity.rbac0.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountResponse {

    private Long id;
    private String email;
    private String username;
    private Set<Long> roles;
    private Set<Long> books;
    private Set<Long> cart;

    public static AccountResponse from(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getEmail(),
                account.getUsername(),
                account.getRoles().stream().map(Role::getId).collect(Collectors.toSet()),
                account.getBooks().stream().map(Book::getId).collect(Collectors.toSet()),
                account.getCart().stream().map(Cart::getId).collect(Collectors.toSet())
        );
    }
}
