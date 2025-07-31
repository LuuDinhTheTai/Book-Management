package com.me.book_management.entity.account;

import com.me.book_management.entity.base.EntityWithUpdater;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "addresses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"accounts"})
public class Address extends EntityWithUpdater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String country;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @Column(name = "is_default")
    private Boolean isDefault = false;

    @ManyToMany(mappedBy = "addresses", fetch = FetchType.LAZY)
    private Set<Account> accounts = new HashSet<>();
} 