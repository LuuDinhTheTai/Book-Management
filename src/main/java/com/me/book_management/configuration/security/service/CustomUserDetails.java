package com.me.book_management.configuration.security.service;

import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.rbac0.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
  
  private String username;
  private String password;
  private List<GrantedAuthority> authorities = new ArrayList<>();
  
  public CustomUserDetails(Account account) {
    this.username = account.getUsername();
    this.password = account.getPassword();
    List<GrantedAuthority> list = new ArrayList<>();
    for (Role role : account.getRoles()) {
      SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName());
      authorities.add(authority);
    }
    this.authorities = list;
  }
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }
  
  @Override
  public String getPassword() {
    return password;
  }
  
  @Override
  public String getUsername() {
    return username;
  }
}
