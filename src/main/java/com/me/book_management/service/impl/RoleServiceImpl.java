package com.me.book_management.service.impl;

import com.me.book_management.entity.rbac0.Role;
import com.me.book_management.repository.rbac0.RoleRepository;
import com.me.book_management.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role createIfNotExists(Role role) {
        log.info("(createIfNotExists) role: {}", role);

        Role existingRole = findByName(role.getName());
        if (existingRole != null) {
            return existingRole;
        }

        return roleRepository.save(role);
    }

    @Override
    public Role findByName(String name) {
        log.info("(findByName) role: {}", name);

        return roleRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
    }
}
