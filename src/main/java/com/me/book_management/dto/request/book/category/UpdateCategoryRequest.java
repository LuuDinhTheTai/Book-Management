package com.me.book_management.dto.request.book.category;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UpdateCategoryRequest extends CreateCategoryRequest {
    
    @NotNull(message = "Category ID is required")
    private Long id;
}
