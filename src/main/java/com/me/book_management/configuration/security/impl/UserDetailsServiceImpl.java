package com.me.book_management.configuration.security.impl;

import com.me.book_management.entity.account.Account;
import com.me.book_management.exception.CustomException;
import com.me.book_management.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
  
  @Autowired
  private AccountService accountService;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account account = accountService.findByUsername(username);
    if (account == null) {
      throw new CustomException("Account not found");
    }
    return new UserDetailsImpl(account);
  }
}
