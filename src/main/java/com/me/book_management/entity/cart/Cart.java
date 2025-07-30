package com.me.book_management.entity.cart;

import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.book.Book;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;
    
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartBook> cartBooks = new ArrayList<>();
    
    private float totalPrice;
    // address id
    private String shippingMethod;
    private String paymentMethod;
    private String status;
}
