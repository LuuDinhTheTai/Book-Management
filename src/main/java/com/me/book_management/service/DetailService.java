package com.me.book_management.service;

import com.me.book_management.dto.request.CreateBookRequest;
import com.me.book_management.entity.book.Detail;

public interface DetailService {

    Detail create(CreateBookRequest.Detail detail);
}
