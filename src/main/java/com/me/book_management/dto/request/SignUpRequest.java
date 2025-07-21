package com.me.book_management.dto.request;

import com.me.book_management.exception.CustomException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SignUpRequest {

    @Email
    private String email;
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public void validate() {
        throw new CustomException("Meo Meo");
    }
}
