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
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RoleDataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final ActionRepository actionRepository;
    private final ResourceRepository resourceRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public void run(String... args) {
        log.info("Starting RoleDataSeeder...");
        seedActions();
        seedResources();
        seedPermissions();
        seedRoles();
        log.info("RoleDataSeeder completed successfully!");
    }

    private void seedActions() {
        log.info("Seeding actions...");
        for (String actionName : Constants.ACTION.list()) {
            if (actionRepository.findByName(actionName).isEmpty()) {
                Action action = new Action();
                action.setName(actionName);
                action.setDescription("Action for " + actionName.toLowerCase());
                actionRepository.save(action);
                log.info("Created action: {}", actionName);
            }
        }
    }

    private void seedResources() {
        log.info("Seeding resources...");
        for (String resourceName : Constants.RESOURCE.list()) {
            if (resourceRepository.findByName(resourceName).isEmpty()) {
                Resource resource = new Resource();
                resource.setName(resourceName);
                resource.setDescription("Resource for " + resourceName.toLowerCase());
                resourceRepository.save(resource);
                log.info("Created resource: {}", resourceName);
            }
        }
    }

    private void seedPermissions() {
        log.info("Seeding permissions...");
        List<Action> actions = actionRepository.findAll();
        List<Resource> resources = resourceRepository.findAll();
        
        for (Resource resource : resources) {
            for (Action action : actions) {
                String permissionName = action.getName() + resource.getName();
                if (permissionRepository.findByName(permissionName).isEmpty()) {
                    Permission permission = new Permission();
                    permission.setName(permissionName);
                    permission.setDescription("Permission to " + action.getName().toLowerCase() + " " + resource.getName().toLowerCase());
                    permission.setResource(resource);
                    permission.setAction(action);
                    permissionRepository.save(permission);
                    log.info("Created permission: {}", permissionName);
                }
            }
        }
    }

    private void seedRoles() {
        log.info("Seeding roles...");
        seedRole(Constants.ROLE.ADMIN, "Administrator with full access", true);
        seedRole(Constants.ROLE.USER, "Regular user with limited access", false);
    }

    private void seedRole(String roleName, String description, boolean isAdmin) {
        if (roleRepository.findByName(roleName).isEmpty()) {
            Role role = new Role();
            role.setName(roleName);
            role.setDescription(description);

            List<Permission> allPermissions = permissionRepository.findAll();
            
            if (isAdmin) {
                // Admin gets all permissions
                role.getPermissions().addAll(allPermissions);
                log.info("Created admin role with {} permissions", allPermissions.size());
            } else {
                // User gets only READ permissions
                allPermissions.stream()
                        .filter(p -> p.getName().startsWith(Constants.ACTION.READ))
                        .forEach(role.getPermissions()::add);
                log.info("Created user role with {} read permissions", 
                        role.getPermissions().size());
            }

            roleRepository.save(role);
            log.info("Created role: {}", roleName);
        }
    }
}
