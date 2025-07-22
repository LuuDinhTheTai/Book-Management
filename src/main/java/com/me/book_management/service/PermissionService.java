package com.me.book_management.service;

import com.me.book_management.entity.rbac0.Permission;

public interface PermissionService {

    Permission createIfNotExists(Permission permission);
    Permission findByName(String name);
}
