package com.me.book_management.seeder;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(0)
public class SeederCoordinator implements CommandLineRunner {

    private final RoleDataSeeder roleDataSeeder;
    private final AccountDataSeeder accountDataSeeder;
    private final BookDataSeeder bookDataSeeder;
    private final CategoryDataSeeder categoryDataSeeder;

    @Override
    public void run(String... args) {
        try {
            roleDataSeeder.run(args);
            accountDataSeeder.run(args);
            categoryDataSeeder.run(args);
            bookDataSeeder.run(args);
            
        } catch (Exception e) {
            System.err.println("SeederCoordinator: Error during seeding process: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 