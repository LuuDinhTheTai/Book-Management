package com.me.book_management.entity.cart;

import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.book.Book;
import jakarta.persistence.*;
import lombok.*;

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
    @ManyToOne
    private Book book;
    @ManyToOne
    private Account account;
    private int qty;
    private float totalPrice;
    // address id
    private String shippingMethod;
    private String paymentMethod;
    private String status;
}
