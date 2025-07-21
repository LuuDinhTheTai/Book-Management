package com.me.book_management.dto.response;

import com.me.book_management.entity.account.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountResponse {

    private String email;
    private String username;

    public static AccountResponse from(Account account) {
        return new AccountResponse(
                account.getEmail(),
                account.getUsername()
        );
    }
}
