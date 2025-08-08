package com.me.book_management.seeder;

import com.me.book_management.constant.Constants;
import com.me.book_management.constant.PERMISSION;
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

import java.util.ArrayList;
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
        seedActions();
        seedResources();
        seedPermissions();
        seedRoles();
    }

    private void seedActions() {
        for (String actionName : PERMISSION.ACTION.list()) {
            if (actionRepository.findByName(actionName).isEmpty()) {
                Action action = new Action();
                action.setName(actionName);

                actionRepository.save(action);
            }
        }
    }

    private void seedResources() {
        List<String> resourceNames = new ArrayList<>(PERMISSION.RESOURCE.list());
        for (String resourceName : resourceNames) {
            if (resourceRepository.findByName(resourceName).isEmpty()) {
                Resource resource = new Resource();
                resource.setName(resourceName);

                resourceRepository.save(resource);
            }
        }
    }

    private void seedPermissions() {
        List<Action> actions = actionRepository.findAll();
        List<Resource> resources = resourceRepository.findAll();
        
        for (Resource resource : resources) {
            for (Action action : actions) {
                String permissionName = action.getName() + " " + resource.getName();
                if (permissionRepository.findByName(permissionName).isEmpty()) {
                    Permission permission = new Permission();
                    permission.setName(permissionName);
                    permission.setResource(resource);
                    permission.setAction(action);

                    permissionRepository.save(permission);
                }
            }
        }
    }

    private void seedRoles() {
        seedRole(Constants.ROLE.ADMIN, "Administrator with full access to all resources", true);
        seedRole(Constants.ROLE.USER, "Regular user with limited access to resources", false);
    }

    private void seedRole(String roleName, String description, boolean isAdmin) {
        if (roleRepository.findByName(roleName).isEmpty()) {
            Role role = new Role();
            role.setName(roleName);
            role.setDescription(description);

            List<Permission> allPermissions = permissionRepository.findAll();
            
            if (isAdmin) {
                role.getPermissions().addAll(allPermissions);

            } else {
                allPermissions.stream()
                        .filter(p -> p.getName().startsWith(PERMISSION.ACTION.GET))
                        .forEach(role.getPermissions()::add);
            }

            roleRepository.save(role);
        }
    }
}
