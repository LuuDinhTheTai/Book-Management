package com.me.book_management.service;

import com.me.book_management.entity.rbac0.Role;

public interface RoleService {

    Role createIfNotExists(Role role);
    Role findByName(String name);
}
