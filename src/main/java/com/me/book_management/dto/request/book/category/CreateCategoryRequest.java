package com.me.book_management.dto.request.book.category;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateCategoryRequest {

    @NotBlank(message = "Name cannot be blank")
    private String name;
    private String description;
}
