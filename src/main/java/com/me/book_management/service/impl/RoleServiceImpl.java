package com.me.book_management.service.impl;

import com.me.book_management.dto.request.role.CreateRoleRequest;
import com.me.book_management.dto.request.role.UpdateRoleRequest;
import com.me.book_management.entity.rbac0.Action;
import com.me.book_management.entity.rbac0.Permission;
import com.me.book_management.entity.rbac0.Resource;
import com.me.book_management.entity.rbac0.Role;
import com.me.book_management.exception.InputException;
import com.me.book_management.exception.NotFoundException;
import com.me.book_management.repository.rbac0.ActionRepository;
import com.me.book_management.repository.rbac0.PermissionRepository;
import com.me.book_management.repository.rbac0.ResourceRepository;
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
    private final ResourceRepository resourceRepository;
    private final ActionRepository actionRepository;

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
        
        for (String permissionString : request.getPermission()) {
            String[] parts = permissionString.split("-");
            if (parts.length != 2) {
                throw new InputException("Invalid permission format: " + permissionString);
            }
            
            Long resourceId = Long.parseLong(parts[0]);
            Long actionId = Long.parseLong(parts[1]);
            
            Resource resource = resourceRepository.findById(resourceId)
                    .orElseThrow(() -> new InputException("Resource not found with id: " + resourceId));
            Action action = actionRepository.findById(actionId)
                    .orElseThrow(() -> new InputException("Action not found with id: " + actionId));
            
            Permission permission = permissionRepository.findByResourceAndAction(resource, action)
                    .orElseThrow(() -> new InputException("Permission not found for resource: " + resource.getName() + " and action: " + action.getName()));
            
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
        for (String permissionString : request.getPermission()) {
            String[] parts = permissionString.split("-");
            if (parts.length != 2) {
                throw new InputException("Invalid permission format: " + permissionString);
            }
            
            Long resourceId = Long.parseLong(parts[0]);
            Long actionId = Long.parseLong(parts[1]);
            
            Resource resource = resourceRepository.findById(resourceId)
                    .orElseThrow(() -> new InputException("Resource not found with id: " + resourceId));
            Action action = actionRepository.findById(actionId)
                    .orElseThrow(() -> new InputException("Action not found with id: " + actionId));
            
            Permission permission = permissionRepository.findByResourceAndAction(resource, action)
                    .orElseThrow(() -> new InputException("Permission not found for resource: " + resource.getName() + " and action: " + action.getName()));
            
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
