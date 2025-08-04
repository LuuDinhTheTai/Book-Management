package com.me.book_management.service;

import com.me.book_management.dto.request.role.CreateRoleRequest;
import com.me.book_management.dto.request.role.UpdateRoleRequest;
import com.me.book_management.entity.rbac0.Role;
import jakarta.validation.Valid;

import java.util.List;

public interface RoleService {

    Role findByName(String name);

    Role find(Long id);

    void create(@Valid CreateRoleRequest request);

    List<Role> list();

    void update(Long id, @Valid UpdateRoleRequest request);

    void delete(Long id);
}
