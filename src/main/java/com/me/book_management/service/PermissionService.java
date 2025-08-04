package com.me.book_management.service;

import com.me.book_management.entity.rbac0.Permission;

import java.util.List;

public interface PermissionService {

    List<Permission> findAll();
}
