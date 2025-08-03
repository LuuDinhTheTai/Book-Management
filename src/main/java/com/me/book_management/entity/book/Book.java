package com.me.book_management.entity.book;

import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.base.EntityWithUpdater;
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
@ToString(exclude = {"detail", "account", "categories"})
public class Book extends EntityWithUpdater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private float price;
    private int qty;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Detail detail;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();
}
