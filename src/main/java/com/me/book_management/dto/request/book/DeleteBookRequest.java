package com.me.book_management.dto.request.book;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DeleteBookRequest {

    private Long bookId;
}
