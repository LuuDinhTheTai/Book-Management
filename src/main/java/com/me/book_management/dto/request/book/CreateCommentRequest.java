package com.me.book_management.dto.request.book;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateCommentRequest {

    private Long bookId;
    @NotBlank(message = "Content cannot be blank")
    @Size(min = 1, max = 100, message = "Content must be at least 1 characters long")
    private String content;

    public void validate() {

    }
}
