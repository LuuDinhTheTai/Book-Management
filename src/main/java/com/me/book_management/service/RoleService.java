package com.me.book_management.service;

import com.me.book_management.entity.rbac0.Role;

public interface RoleService {

    Role findByName(String name);
}
