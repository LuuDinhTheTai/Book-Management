package com.me.book_management.seeder;

import com.me.book_management.constant.Constants;
import com.me.book_management.entity.rbac0.Action;
import com.me.book_management.entity.rbac0.Permission;
import com.me.book_management.entity.rbac0.Resource;
import com.me.book_management.entity.rbac0.Role;
import com.me.book_management.repository.rbac0.ActionRepository;
import com.me.book_management.repository.rbac0.PermissionRepository;
import com.me.book_management.repository.rbac0.ResourceRepository;
import com.me.book_management.repository.rbac0.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(1)
public class RoleDataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final ActionRepository actionRepository;
    private final ResourceRepository resourceRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public void run(String... args) {
        seedActions();
        seedResources();
        seedPermissions();
        seedRoles();
    }

    private void seedActions() {
        if (!actionRepository.existsByName(Constants.ACTION.CREATE)) {
            actionRepository.save(new Action(Constants.ACTION.CREATE));
        }
        if (!actionRepository.existsByName(Constants.ACTION.READ)) {
            actionRepository.save(new Action(Constants.ACTION.READ));
        }
        if (!actionRepository.existsByName(Constants.ACTION.UPDATE)) {
            actionRepository.save(new Action(Constants.ACTION.UPDATE));
        }
        if (!actionRepository.existsByName(Constants.ACTION.DELETE)) {
            actionRepository.save(new Action(Constants.ACTION.DELETE));
        }
    }

    private void seedResources() {
        if (!resourceRepository.existsByName(Constants.RESOURCE.ROLE)) {
            resourceRepository.save(new Resource(Constants.RESOURCE.ROLE));
        }
        if (!resourceRepository.existsByName(Constants.RESOURCE.BOOK)) {
            resourceRepository.save(new Resource(Constants.RESOURCE.BOOK));
        }
        if (!resourceRepository.existsByName(Constants.RESOURCE.COMMENT)) {
            resourceRepository.save(new Resource(Constants.RESOURCE.COMMENT));
        }
        if (!resourceRepository.existsByName(Constants.RESOURCE.ACCOUNT)) {
            resourceRepository.save(new Resource(Constants.RESOURCE.ACCOUNT));
        }
        if (!resourceRepository.existsByName(Constants.RESOURCE.CART)) {
            resourceRepository.save(new Resource(Constants.RESOURCE.CART));
        }
        if (!resourceRepository.existsByName(Constants.RESOURCE.CATEGORY)) {
            resourceRepository.save(new Resource(Constants.RESOURCE.CATEGORY));
        }
    }

    private void seedPermissions() {
        for (String perm : new String[]{
                Constants.PERMISSION.CREATE_BOOK,
                Constants.PERMISSION.READ_BOOK,
                Constants.PERMISSION.UPDATE_BOOK,
                Constants.PERMISSION.DELETE_BOOK,
                Constants.PERMISSION.CREATE_COMMENT,
                Constants.PERMISSION.READ_COMMENT,
                Constants.PERMISSION.UPDATE_COMMENT,
                Constants.PERMISSION.DELETE_COMMENT,
                Constants.PERMISSION.CREATE_ACCOUNT,
                Constants.PERMISSION.READ_ACCOUNT,
                Constants.PERMISSION.UPDATE_ACCOUNT,
                Constants.PERMISSION.DELETE_ACCOUNT,
                Constants.PERMISSION.CREATE_CART,
                Constants.PERMISSION.READ_CART,
                Constants.PERMISSION.UPDATE_CART,
                Constants.PERMISSION.DELETE_CART,
                Constants.PERMISSION.CREATE_ROLE,
                Constants.PERMISSION.READ_ROLE,
                Constants.PERMISSION.UPDATE_ROLE,
                Constants.PERMISSION.DELETE_ROLE,
                Constants.PERMISSION.CREATE_CATEGORY,
                Constants.PERMISSION.READ_CATEGORY,
                Constants.PERMISSION.UPDATE_CATEGORY,
                Constants.PERMISSION.DELETE_CATEGORY
        }) {
            if (permissionRepository.findByName(perm).isEmpty()) {
                String[] parts = perm.split(Constants.PERMISSION.BRIDGE);
                String actionName = parts[0];
                String resourceName = parts[1];
                Action act = actionRepository.findByName(actionName).orElse(null);
                Resource res = resourceRepository.findByName(resourceName).orElse(null);
                permissionRepository.save(new Permission(perm, act, res));
            }
        }
    }

    private void seedRoles() {
        seedRole(Constants.ROLE.ADMIN, true);
        seedRole(Constants.ROLE.USER, false);
    }

    private void seedRole(String roleName, boolean isAdmin) {
        if (roleRepository.findByName(roleName).isPresent()) return;
        Role role = new Role();
        role.setName(roleName);

        if (isAdmin) {
            permissionRepository.findAll().forEach(role.getPermissions()::add);
        } else {
            permissionRepository.findByName(Constants.PERMISSION.CREATE_BOOK).ifPresent(role.getPermissions()::add);
            permissionRepository.findByName(Constants.PERMISSION.READ_BOOK).ifPresent(role.getPermissions()::add);
            permissionRepository.findByName(Constants.PERMISSION.UPDATE_BOOK).ifPresent(role.getPermissions()::add);
            permissionRepository.findByName(Constants.PERMISSION.DELETE_BOOK).ifPresent(role.getPermissions()::add);
            permissionRepository.findByName(Constants.PERMISSION.CREATE_COMMENT).ifPresent(role.getPermissions()::add);
            permissionRepository.findByName(Constants.PERMISSION.READ_COMMENT).ifPresent(role.getPermissions()::add);
            permissionRepository.findByName(Constants.PERMISSION.CREATE_ACCOUNT).ifPresent(role.getPermissions()::add);
            permissionRepository.findByName(Constants.PERMISSION.READ_ACCOUNT).ifPresent(role.getPermissions()::add);
            permissionRepository.findByName(Constants.PERMISSION.UPDATE_ACCOUNT).ifPresent(role.getPermissions()::add);
            permissionRepository.findByName(Constants.PERMISSION.CREATE_CART).ifPresent(role.getPermissions()::add);
            permissionRepository.findByName(Constants.PERMISSION.READ_CART).ifPresent(role.getPermissions()::add);
            permissionRepository.findByName(Constants.PERMISSION.UPDATE_CART).ifPresent(role.getPermissions()::add);
            permissionRepository.findByName(Constants.PERMISSION.DELETE_CART).ifPresent(role.getPermissions()::add);
        }
        roleRepository.save(role);
    }
}
