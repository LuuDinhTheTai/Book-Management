package com.me.book_management.seeder;

import com.me.book_management.constant.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(0)
public class SeederCoordinator implements CommandLineRunner {

    private final RoleDataSeeder roleDataSeeder;
    private final AccountDataSeeder accountDataSeeder;
    private final CategoryDataSeeder categoryDataSeeder;
    private final BookDataSeeder bookDataSeeder;

    @Override
    public void run(String... args) {
        try {
            roleDataSeeder.run(args);
            accountDataSeeder.run(args);
            categoryDataSeeder.run(args);
            bookDataSeeder.run(args);
            
        } catch (Exception e) {
            log.error("Error during seeding process: ", e);
            throw new RuntimeException("Seeder process failed", e);
        }
    }
} 