package com.me.book_management.seeder;

import com.me.book_management.constant.Constants;
import com.me.book_management.entity.rbac0.Action;
import com.me.book_management.entity.rbac0.Permission;
import com.me.book_management.entity.rbac0.Resource;
import com.me.book_management.entity.rbac0.Role;
import com.me.book_management.service.ActionService;
import com.me.book_management.service.PermissionService;
import com.me.book_management.service.ResourceService;
import com.me.book_management.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleDataSeeder implements CommandLineRunner {

    private final RoleService service;
    private final ActionService actionService;
    private final ResourceService resourceService;
    private final PermissionService permissionService;

    @Override
    public void run(String... args) {
        seedActions();
        seedResources();
        seedPermissions();
        seedRoles();
    }

    private void seedRoles() {
        Role ADMIN = seedAdminRole();
        Role USER = seedUserRole();
        service.createIfNotExists(ADMIN);
        service.createIfNotExists(USER);
    }

    private Role seedAdminRole() {
        Role ADMIN = new Role();
        ADMIN.setName(Constants.ROLE.ADMIN);
        ADMIN.getPermissions().add(permissionService.findByName(Constants.PERMISSION.CREATE_BOOK));
        ADMIN.getPermissions().add(permissionService.findByName(Constants.PERMISSION.READ_BOOK));
        ADMIN.getPermissions().add(permissionService.findByName(Constants.PERMISSION.UPDATE_BOOK));
        ADMIN.getPermissions().add(permissionService.findByName(Constants.PERMISSION.DELETE_BOOK));
        ADMIN.getPermissions().add(permissionService.findByName(Constants.PERMISSION.CREATE_COMMENT));
        ADMIN.getPermissions().add(permissionService.findByName(Constants.PERMISSION.READ_COMMENT));
        ADMIN.getPermissions().add(permissionService.findByName(Constants.PERMISSION.UPDATE_COMMENT));
        ADMIN.getPermissions().add(permissionService.findByName(Constants.PERMISSION.DELETE_COMMENT));
        ADMIN.getPermissions().add(permissionService.findByName(Constants.PERMISSION.CREATE_ACCOUNT));
        ADMIN.getPermissions().add(permissionService.findByName(Constants.PERMISSION.READ_ACCOUNT));
        ADMIN.getPermissions().add(permissionService.findByName(Constants.PERMISSION.UPDATE_ACCOUNT));
        ADMIN.getPermissions().add(permissionService.findByName(Constants.PERMISSION.DELETE_ACCOUNT));
        ADMIN.getPermissions().add(permissionService.findByName(Constants.PERMISSION.CREATE_CART));
        ADMIN.getPermissions().add(permissionService.findByName(Constants.PERMISSION.READ_CART));
        ADMIN.getPermissions().add(permissionService.findByName(Constants.PERMISSION.UPDATE_CART));
        ADMIN.getPermissions().add(permissionService.findByName(Constants.PERMISSION.DELETE_CART));
        ADMIN.getPermissions().add(permissionService.findByName(Constants.PERMISSION.CREATE_ROLE));
        ADMIN.getPermissions().add(permissionService.findByName(Constants.PERMISSION.READ_ROLE));
        ADMIN.getPermissions().add(permissionService.findByName(Constants.PERMISSION.UPDATE_ROLE));
        ADMIN.getPermissions().add(permissionService.findByName(Constants.PERMISSION.DELETE_ROLE));
        ADMIN.getPermissions().add(permissionService.findByName(Constants.PERMISSION.CREATE_CATEGORY));
        ADMIN.getPermissions().add(permissionService.findByName(Constants.PERMISSION.READ_CATEGORY));
        ADMIN.getPermissions().add(permissionService.findByName(Constants.PERMISSION.UPDATE_CATEGORY));
        ADMIN.getPermissions().add(permissionService.findByName(Constants.PERMISSION.DELETE_CATEGORY));
        return ADMIN;
    }

    private Role seedUserRole() {
        Role USER = new Role();
        USER.setName(Constants.ROLE.USER);
        USER.getPermissions().add(permissionService.findByName(Constants.PERMISSION.CREATE_BOOK));
        USER.getPermissions().add(permissionService.findByName(Constants.PERMISSION.READ_BOOK));
        USER.getPermissions().add(permissionService.findByName(Constants.PERMISSION.UPDATE_BOOK));
        USER.getPermissions().add(permissionService.findByName(Constants.PERMISSION.DELETE_BOOK));
        USER.getPermissions().add(permissionService.findByName(Constants.PERMISSION.CREATE_COMMENT));
        USER.getPermissions().add(permissionService.findByName(Constants.PERMISSION.READ_COMMENT));
        USER.getPermissions().add(permissionService.findByName(Constants.PERMISSION.CREATE_ACCOUNT));
        USER.getPermissions().add(permissionService.findByName(Constants.PERMISSION.READ_ACCOUNT));
        USER.getPermissions().add(permissionService.findByName(Constants.PERMISSION.UPDATE_ACCOUNT));
        USER.getPermissions().add(permissionService.findByName(Constants.PERMISSION.CREATE_CART));
        USER.getPermissions().add(permissionService.findByName(Constants.PERMISSION.READ_CART));
        USER.getPermissions().add(permissionService.findByName(Constants.PERMISSION.UPDATE_CART));
        USER.getPermissions().add(permissionService.findByName(Constants.PERMISSION.DELETE_CART));
        return USER;
    }

    private void seedActions() {
        Action create = new Action();
        create.setName(Constants.ACTION.CREATE);
        Action read = new Action();
        read.setName(Constants.ACTION.READ);
        Action update = new Action();
        update.setName(Constants.ACTION.UPDATE);
        Action delete = new Action();
        delete.setName(Constants.ACTION.DELETE);
        actionService.createIfNotExists(create);
        actionService.createIfNotExists(read);
        actionService.createIfNotExists(update);
        actionService.createIfNotExists(delete);
    }

    private void seedResources() {
        Resource book = new Resource();
        book.setName(Constants.RESOURCE.BOOK);
        Resource comment = new Resource();
        comment.setName(Constants.RESOURCE.COMMENT);
        Resource account = new Resource();
        account.setName(Constants.RESOURCE.ACCOUNT);
        Resource cart = new Resource();
        cart.setName(Constants.RESOURCE.CART);
        Resource role = new Resource();
        role.setName(Constants.RESOURCE.ROLE);
        Resource category = new Resource();
        category.setName(Constants.RESOURCE.CATEGORY);
        resourceService.createIfNotExists(book);
        resourceService.createIfNotExists(comment);
        resourceService.createIfNotExists(account);
        resourceService.createIfNotExists(cart);
        resourceService.createIfNotExists(role);
        resourceService.createIfNotExists(category);
    }

    private void seedPermissions() {
        Resource book = resourceService.findByName(Constants.RESOURCE.BOOK);
        Resource comment = resourceService.findByName(Constants.RESOURCE.COMMENT);
        Resource account = resourceService.findByName(Constants.RESOURCE.ACCOUNT);
        Resource cart = resourceService.findByName(Constants.RESOURCE.CART);
        Resource role = resourceService.findByName(Constants.RESOURCE.ROLE);
        Resource category = resourceService.findByName(Constants.RESOURCE.CATEGORY);
        Action create = actionService.findByName(Constants.ACTION.CREATE);
        Action read = actionService.findByName(Constants.ACTION.READ);
        Action update = actionService.findByName(Constants.ACTION.UPDATE);
        Action delete = actionService.findByName(Constants.ACTION.DELETE);
        Permission createBook = new Permission(Constants.PERMISSION.CREATE_BOOK, create, book);
        Permission readBook = new Permission(Constants.PERMISSION.READ_BOOK, read, book);
        Permission updateBook = new Permission(Constants.PERMISSION.UPDATE_BOOK, update, book);
        Permission deleteBook = new Permission(Constants.PERMISSION.DELETE_BOOK, delete, book);
        Permission createComment = new Permission(Constants.PERMISSION.CREATE_COMMENT, create, comment);
        Permission readComment = new Permission(Constants.PERMISSION.READ_COMMENT, read, comment);
        Permission updateComment = new Permission(Constants.PERMISSION.UPDATE_COMMENT, update, comment);
        Permission deleteComment = new Permission(Constants.PERMISSION.DELETE_COMMENT, delete, comment);
        Permission createAccount = new Permission(Constants.PERMISSION.CREATE_ACCOUNT, create, account);
        Permission readAccount = new Permission(Constants.PERMISSION.READ_ACCOUNT, read, account);
        Permission updateAccount = new Permission(Constants.PERMISSION.UPDATE_ACCOUNT, update, account);
        Permission deleteAccount = new Permission(Constants.PERMISSION.DELETE_ACCOUNT, delete, account);
        Permission createCart = new Permission(Constants.PERMISSION.CREATE_CART, create, cart);
        Permission readCart = new Permission(Constants.PERMISSION.READ_CART, read, cart);
        Permission updateCart = new Permission(Constants.PERMISSION.UPDATE_CART, update, cart);
        Permission deleteCart = new Permission(Constants.PERMISSION.DELETE_CART, delete, cart);
        Permission createRole = new Permission(Constants.PERMISSION.CREATE_ROLE, create, role);
        Permission readRole = new Permission(Constants.PERMISSION.READ_ROLE, read, role);
        Permission updateRole = new Permission(Constants.PERMISSION.UPDATE_ROLE, update, role);
        Permission deleteRole = new Permission(Constants.PERMISSION.DELETE_ROLE, delete, role);
        Permission createCategory = new Permission(Constants.PERMISSION.CREATE_CATEGORY, create, category);
        Permission readCategory = new Permission(Constants.PERMISSION.READ_CATEGORY, read, category);
        Permission updateCategory = new Permission(Constants.PERMISSION.UPDATE_CATEGORY, update, category);
        Permission deleteCategory = new Permission(Constants.PERMISSION.DELETE_CATEGORY, delete, category);
        permissionService.createIfNotExists(createBook);
        permissionService.createIfNotExists(readBook);
        permissionService.createIfNotExists(updateBook);
        permissionService.createIfNotExists(deleteBook);
        permissionService.createIfNotExists(createComment);
        permissionService.createIfNotExists(readComment);
        permissionService.createIfNotExists(updateComment);
        permissionService.createIfNotExists(deleteComment);
        permissionService.createIfNotExists(createAccount);
        permissionService.createIfNotExists(readAccount);
        permissionService.createIfNotExists(updateAccount);
        permissionService.createIfNotExists(deleteAccount);
        permissionService.createIfNotExists(createCart);
        permissionService.createIfNotExists(readCart);
        permissionService.createIfNotExists(updateCart);
        permissionService.createIfNotExists(deleteCart);
        permissionService.createIfNotExists(createRole);
        permissionService.createIfNotExists(readRole);
        permissionService.createIfNotExists(updateRole);
        permissionService.createIfNotExists(deleteRole);
        permissionService.createIfNotExists(createCategory);
        permissionService.createIfNotExists(readCategory);
        permissionService.createIfNotExists(updateCategory);
        permissionService.createIfNotExists(deleteCategory);
    }
}
