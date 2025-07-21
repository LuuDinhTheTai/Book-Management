package com.me.book_management.service;

import com.me.book_management.entity.account.Account;

public interface AccountService {

    Account findByUsername(String username);
}
