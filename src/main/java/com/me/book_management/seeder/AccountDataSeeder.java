package com.me.book_management.seeder;

import com.me.book_management.constant.Constants;
import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.rbac0.Role;
import com.me.book_management.repository.account.AccountRepository;
import com.me.book_management.repository.rbac0.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Order(2)
public class AccountDataSeeder implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seedAccounts();
    }

    private void seedAccounts() {
        if (!accountRepository.existsByUsername("admin")) {
            Account adminAccount = new Account();
            adminAccount.setEmail("admin@bookmanagement.com");
            adminAccount.setUsername("admin");
            adminAccount.setPassword(passwordEncoder.encode("admin"));
            
            Optional<Role> adminRole = roleRepository.findByName(Constants.ROLE.ADMIN);
            if (adminRole.isPresent()) {
                adminAccount.getRoles().add(adminRole.get());
            }
            
            accountRepository.save(adminAccount);
        }

        if (!accountRepository.existsByUsername("user")) {
            Account userAccount = new Account();
            userAccount.setEmail("user@bookmanagement.com");
            userAccount.setUsername("user");
            userAccount.setPassword(passwordEncoder.encode("user"));
            
            Optional<Role> userRole = roleRepository.findByName(Constants.ROLE.USER);
            if (userRole.isPresent()) {
                userAccount.getRoles().add(userRole.get());
            }
            
            accountRepository.save(userAccount);
        }
    }
} 