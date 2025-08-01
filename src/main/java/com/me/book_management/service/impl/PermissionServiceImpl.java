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
}
