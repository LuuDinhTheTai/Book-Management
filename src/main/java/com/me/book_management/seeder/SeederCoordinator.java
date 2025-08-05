package com.me.book_management.seeder;

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
        log.info("Starting SeederCoordinator...");
        try {
            // Thứ tự quan trọng: Roles -> Accounts -> Categories -> Books
            log.info("=== Starting RoleDataSeeder ===");
            roleDataSeeder.run(args);
            
            log.info("=== Starting AccountDataSeeder ===");
            accountDataSeeder.run(args);
            
            log.info("=== Starting CategoryDataSeeder ===");
            categoryDataSeeder.run(args);
            
            log.info("=== Starting BookDataSeeder ===");
            bookDataSeeder.run(args);
            
            log.info("All seeders completed successfully!");
        } catch (Exception e) {
            log.error("Error during seeding process: ", e);
            throw new RuntimeException("Seeder process failed", e);
        }
    }
} 