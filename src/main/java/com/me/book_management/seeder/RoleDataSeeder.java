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

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleDataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final ActionRepository actionRepository;
    private final ResourceRepository resourceRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public void run(String... args) {
        System.out.println("RoleDataSeeder: Starting role and permission seeding...");
        seedActions();
        seedResources();
        seedPermissions();
        seedRoles();
        System.out.println("RoleDataSeeder: Completed role and permission seeding.");
    }

    private void seedActions() {
        List<String> actions = Arrays.asList(
            Constants.ACTION.CREATE,
            Constants.ACTION.READ,
            Constants.ACTION.UPDATE,
            Constants.ACTION.DELETE
        );
        
        for (String actionName : actions) {
            if (!actionRepository.existsByName(actionName)) {
                actionRepository.save(new Action(actionName));
            }
        }
    }

    private void seedResources() {
        List<String> resources = Arrays.asList(
            Constants.RESOURCE.ROLE,
            Constants.RESOURCE.BOOK,
            Constants.RESOURCE.COMMENT,
            Constants.RESOURCE.ACCOUNT,
            Constants.RESOURCE.CART,
            Constants.RESOURCE.CATEGORY
        );
        
        for (String resourceName : resources) {
            if (!resourceRepository.existsByName(resourceName)) {
                resourceRepository.save(new Resource(resourceName));
            }
        }
    }

    private void seedPermissions() {
        List<String> permissions = Arrays.asList(
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
        );
        
        for (String permissionName : permissions) {
            if (permissionRepository.findByName(permissionName).isEmpty()) {
                String[] parts = permissionName.split(Constants.PERMISSION.BRIDGE);
                String actionName = parts[0];
                String resourceName = parts[1];
                
                Action action = actionRepository.findByName(actionName).orElse(null);
                Resource resource = resourceRepository.findByName(resourceName).orElse(null);
                
                if (action != null && resource != null) {
                    permissionRepository.save(new Permission(permissionName, action, resource));
                }
            }
        }
    }

    private void seedRoles() {
        seedRole(Constants.ROLE.ADMIN, true);
        seedRole(Constants.ROLE.USER, false);
    }

    private void seedRole(String roleName, boolean isAdmin) {
        if (roleRepository.findByName(roleName).isPresent()) {
            return;
        }
        
        Role role = new Role();
        role.setName(roleName);

        if (isAdmin) {
            // Admin gets all permissions
            permissionRepository.findAll().forEach(role.getPermissions()::add);
        } else {
            // User gets limited permissions
            List<String> userPermissions = Arrays.asList(
                Constants.PERMISSION.CREATE_BOOK,
                Constants.PERMISSION.READ_BOOK,
                Constants.PERMISSION.UPDATE_BOOK,
                Constants.PERMISSION.DELETE_BOOK,
                Constants.PERMISSION.CREATE_COMMENT,
                Constants.PERMISSION.READ_COMMENT,
                Constants.PERMISSION.CREATE_ACCOUNT,
                Constants.PERMISSION.READ_ACCOUNT,
                Constants.PERMISSION.UPDATE_ACCOUNT,
                Constants.PERMISSION.CREATE_CART,
                Constants.PERMISSION.READ_CART,
                Constants.PERMISSION.UPDATE_CART,
                Constants.PERMISSION.DELETE_CART
            );
            
            for (String permissionName : userPermissions) {
                permissionRepository.findByName(permissionName).ifPresent(role.getPermissions()::add);
            }
        }
        
        roleRepository.save(role);
    }
}
