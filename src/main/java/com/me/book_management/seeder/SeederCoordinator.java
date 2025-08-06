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
        log.info("Starting SeederCoordinator for Book Management System...");
        log.info("Using Constants from: {}", Constants.class.getSimpleName());
        
        try {
            // Execution order is critical for data integrity
            // 1. Roles (needed for accounts)
            // 2. Accounts (needed for books)
            // 3. Categories (needed for books)
            // 4. Books (depends on all above)
            
            log.info("=== Step 1: Seeding {} and {} roles ===", Constants.ROLE.ADMIN, Constants.ROLE.USER);
            roleDataSeeder.run(args);
            
            log.info("=== Step 2: Seeding accounts with roles ===");
            accountDataSeeder.run(args);
            
            log.info("=== Step 3: Seeding book categories ===");
            categoryDataSeeder.run(args);
            
            log.info("=== Step 4: Seeding books with {} status ===", Constants.BOOK_STATUS.AVAILABLE);
            bookDataSeeder.run(args);
            
            log.info("=== All seeders completed successfully! ===");
            log.info("System is ready with:");
            log.info("- {} roles: {}", Constants.ROLE.list().size(), Constants.ROLE.list());
            log.info("- Book statuses: {}", Constants.BOOK_STATUS.list());
            log.info("- Book formats: {}", Constants.BOOK_FORMAT.list());
            log.info("- Book languages: {}", Constants.BOOK_LANGUAGE.list());
            log.info("- Payment methods: {}", Constants.PAYMENT_METHOD.list());
            log.info("- Shipping methods: {}", Constants.SHIPPING_METHOD.list());
            
        } catch (Exception e) {
            log.error("Error during seeding process: ", e);
            throw new RuntimeException("Seeder process failed", e);
        }
    }
} 