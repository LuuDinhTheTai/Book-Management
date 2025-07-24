package com.me.book_management.entity.book;

import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.base.EntityWithUpdater;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Comment extends EntityWithUpdater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne
    private Account account;
    @ManyToOne
    private Book book;
}
