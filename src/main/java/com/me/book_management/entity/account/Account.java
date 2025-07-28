package com.me.book_management.entity.account;


import com.me.book_management.entity.base.EntityWithUpdater;
import com.me.book_management.entity.book.Book;
import com.me.book_management.entity.cart.Cart;
import com.me.book_management.entity.rbac0.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Account extends EntityWithUpdater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "account_role",
        joinColumns = @JoinColumn(name = "account_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "account")
    private Set<Book> books = new HashSet<>();

    @OneToMany(mappedBy = "account")
    private List<Cart> cart = new ArrayList<>();
}
