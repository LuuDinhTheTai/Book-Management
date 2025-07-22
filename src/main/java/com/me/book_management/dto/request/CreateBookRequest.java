package com.me.book_management.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateBookRequest {

    private String name;
    private String price;
    private int qty;
    private String status;
    private Detail detail;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class Detail {

        private String isbn;
        private String title;
        private String subtitle;
        private String author;
        private String publisher;
        private String publishedDate;
        private String description;
        private String pageCount;
        private String language;
        private String format;

        public void validate() {

        }
    }

    public void validate() {
        detail.validate();

    }
}
