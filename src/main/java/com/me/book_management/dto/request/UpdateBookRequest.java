package com.me.book_management.dto.request;

import com.me.book_management.entity.book.Book;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UpdateBookRequest extends CreateBookRequest {

    private Long id;

    @Override
    public void validate() {
        super.validate();

    }
}
