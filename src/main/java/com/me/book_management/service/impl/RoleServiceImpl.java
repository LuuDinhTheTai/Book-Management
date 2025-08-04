package com.me.book_management.service.impl;

import com.me.book_management.dto.request.role.CreateRoleRequest;
import com.me.book_management.dto.request.role.UpdateRoleRequest;
import com.me.book_management.entity.rbac0.Permission;
import com.me.book_management.entity.rbac0.Role;
import com.me.book_management.exception.InputException;
import com.me.book_management.exception.NotFoundException;
import com.me.book_management.repository.rbac0.PermissionRepository;
import com.me.book_management.repository.rbac0.RoleRepository;
import com.me.book_management.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public Role findByName(String name) {
        log.info("(find) role: {}", name);

        return roleRepository.findByName(name)
                .orElse(null);
    }

    @Override
    public Role find(Long id) {
        Role role =  roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role not found"));

        log.info("(find) role response: {}", role);
        return role;
    }

    @Override
    public void create(CreateRoleRequest request) {
        log.info("(create) role: {}", request);
        Role role = new Role();
        role.setName(request.getName());
        role.setDescription(request.getDescription());
        for (Long permId : request.getPermission()) {
            Permission permission = permissionRepository.findById(permId)
                    .orElseThrow(() -> new InputException("Permission not found"));
            role.getPermissions().add(permission);
        }
        roleRepository.save(role);
    }

    @Override
    public List<Role> list() {
        return roleRepository.findAll();
    }

    @Override
    public void update(Long id, UpdateRoleRequest request) {
        log.info("(update) role: {} {}", id, request);
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new InputException("Role not found"));

        role.setName(request.getName());
        role.setDescription(request.getDescription());

        List<Permission> permissions = new ArrayList<>();
        for (Long permId : request.getPermission()) {
            Permission permission = permissionRepository.findById(permId)
                    .orElseThrow(() -> new InputException("Permission not found"));
            permissions.add(permission);
        }
        role.setPermissions(permissions);

        roleRepository.save(role);
        log.info("(update) role response: {}", role);
    }

    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }
}
