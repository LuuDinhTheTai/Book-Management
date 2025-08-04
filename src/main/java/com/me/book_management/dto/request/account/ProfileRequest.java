package com.me.book_management.dto.request.account;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProfileRequest {

    private int bookPage = 1;
    private int bookSize = 10;
    private int cartPage = 1;
    private int cartSize = 10;
    private int commentPage = 1;
    private int commentSize = 10;
    private int orderPage = 1;
    private int orderSize = 10;
    private String tab = "books";
}
