package com.me.book_management.dto.request.auth;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SignUpRequest {

    @Email(message = "Email is not valid")
    @NotBlank(message = "Email cannot be blank")
    private String email;
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 4, max = 10, message = "Username must be at least 4 characters long")
    private String username;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 4, max = 10, message = "Password must be at least 4 characters long")
    private String password;

    public void validate() {

    }
}
