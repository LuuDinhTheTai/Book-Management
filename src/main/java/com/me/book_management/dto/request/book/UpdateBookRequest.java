package com.me.book_management.dto.request.book;

import com.me.book_management.entity.book.Book;

import lombok.*;

public class UpdateBookRequest extends CreateBookRequest {

    public static void toBook(UpdateBookRequest resource, Book target) {
        Detail resourceDetail = resource.getDetail();

        target.setName(resource.getName());
        target.setPrice(resource.getPrice());
        target.setQty(resource.getQty());
        target.setStatus(resource.getStatus());
        Detail.toDetail(resourceDetail, target.getDetail());
    }
}
