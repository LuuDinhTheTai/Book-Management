package com.me.book_management.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ForgotPasswordRequest {

    @Email
    private String email;
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public void validate() {

    }
}
