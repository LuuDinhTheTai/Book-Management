package com.me.book_management.configuration.security.impl;

import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.rbac0.Permission;
import com.me.book_management.entity.rbac0.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
  
  private String username;
  private String password;
  private List<GrantedAuthority> authorities;
  
  public UserDetailsImpl(Account account) {
    this.username = account.getUsername();
    this.password = account.getPassword();
    List<GrantedAuthority> list = new ArrayList<>();
    for (Role role : account.getRoles()) {
      SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName());
      list.add(authority);
    }
    for (Role role : account.getRoles()) {
      for (Permission permission : role.getPermissions()) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission.getName());
        list.add(authority);
      }
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
