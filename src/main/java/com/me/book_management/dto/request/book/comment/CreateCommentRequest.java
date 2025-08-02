package com.me.book_management.dto.request.book.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateCommentRequest {

    @NotNull(message = "Book ID cannot be null")
    private Long bookId;
    
    @NotBlank(message = "Content cannot be blank")
    @Size(min = 1, max = 100, message = "Content must be at least 1 characters long")
    private String content;

    public void validate() {

    }
}
