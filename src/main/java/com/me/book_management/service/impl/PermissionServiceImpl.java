package com.me.book_management.service.impl;

import com.me.book_management.entity.rbac0.Permission;
import com.me.book_management.repository.rbac0.PermissionRepository;
import com.me.book_management.service.PermissionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public Permission createIfNotExists(Permission permission) {
        log.info("(create) permission: {}", permission);

        Permission existingPermission = findByName(permission.getName());
        if (existingPermission != null) {
            return existingPermission;
        }

        return permissionRepository.save(permission);
    }

    @Override
    public Permission findByName(String name) {
        log.info("(find) permission: {}", name);

        return permissionRepository.findByName(name)
                .orElse(null);
    }
}
