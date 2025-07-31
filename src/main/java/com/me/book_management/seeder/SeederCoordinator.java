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
    private final AddressDataSeeder addressDataSeeder;

    @Override
    public void run(String... args) {
        System.out.println("=== STARTING DATABASE SEEDING PROCESS ===");
        System.out.println("SeederCoordinator: Initializing all seeders...");
        
        try {
            // Execute seeders in order
            System.out.println("\n--- Step 1: Seeding Roles and Permissions ---");
            roleDataSeeder.run(args);
            
            System.out.println("\n--- Step 2: Seeding Accounts ---");
            accountDataSeeder.run(args);
            
            System.out.println("\n--- Step 3: Seeding Categories ---");
            categoryDataSeeder.run(args);
            
            System.out.println("\n--- Step 4: Seeding Books and Details ---");
            bookDataSeeder.run(args);
            
            System.out.println("\n--- Step 5: Seeding Addresses ---");
            addressDataSeeder.run(args);
            
            System.out.println("\n=== DATABASE SEEDING COMPLETED SUCCESSFULLY ===");
            System.out.println("SeederCoordinator: All seeders have been executed successfully.");
            System.out.println("SeederCoordinator: Database is now populated with sample data.");
            
        } catch (Exception e) {
            System.err.println("SeederCoordinator: Error during seeding process: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 