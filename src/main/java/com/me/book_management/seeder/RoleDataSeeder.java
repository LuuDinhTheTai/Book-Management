package com.me.book_management.seeder;

import com.me.book_management.constant.Constants;
import com.me.book_management.entity.rbac0.Role;
import com.me.book_management.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleDataSeeder implements CommandLineRunner {

    private final RoleService service;

    @Override
    public void run(String... args) {
        Role ADMIN = new Role();
        ADMIN.setName(Constants.ROLE.ADMIN);
        Role USER = new Role();
        USER.setName(Constants.ROLE.USER);
        service.createIfNotExists(ADMIN);
        service.createIfNotExists(USER);
    }
}
