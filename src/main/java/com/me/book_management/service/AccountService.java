package com.me.book_management.service;

import com.me.book_management.dto.request.account.UpdateAccountRequest;
import com.me.book_management.dto.response.account.AccountResponse;
import com.me.book_management.entity.account.Account;

public interface AccountService {

    AccountResponse find(Long id);
    Account findByUsername(String username);
    AccountResponse update(Long id, UpdateAccountRequest request);
    void delete(Long id);
    long count();
}
