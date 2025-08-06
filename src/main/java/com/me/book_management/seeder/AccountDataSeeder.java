package com.me.book_management.seeder;

import com.me.book_management.constant.Constants;
import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.rbac0.Role;
import com.me.book_management.repository.account.AccountRepository;
import com.me.book_management.repository.rbac0.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountDataSeeder implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        log.info("Starting AccountDataSeeder...");
        seedAccounts();
        log.info("AccountDataSeeder completed successfully!");
    }

    private void seedAccounts() {
        log.info("Seeding accounts using Constants.ROLE...");
        List<AccountData> accountsToSeed = List.of(
                new AccountData("admin", "admin@bookmanagement.com", "admin123", Constants.ROLE.ADMIN),
                new AccountData("user", "user@bookmanagement.com", "user123", Constants.ROLE.USER),
                new AccountData("manager", "manager@bookmanagement.com", "manager123", Constants.ROLE.ADMIN)
        );

        for (AccountData accountData : accountsToSeed) {
            if (!accountRepository.existsByUsername(accountData.username)) {
                Account account = createAccount(accountData);
                accountRepository.save(account);
                log.info("Created account: {} with role: {}", accountData.username, accountData.roleName);
            } else {
                log.info("Account already exists: {}", accountData.username);
            }
        }
    }

    private Account createAccount(AccountData accountData) {
        Account account = new Account();
        account.setEmail(accountData.email);
        account.setUsername(accountData.username);
        account.setPassword(passwordEncoder.encode(accountData.password));
        
        Optional<Role> role = roleRepository.findByName(accountData.roleName);
        if (role.isPresent()) {
            account.getRoles().add(role.get());
            log.info("Assigned role '{}' to account '{}'", accountData.roleName, accountData.username);
        } else {
            log.warn("Role '{}' not found for account '{}'", accountData.roleName, accountData.username);
        }
        
        return account;
    }

    private static class AccountData {
        private final String username;
        private final String email;
        private final String password;
        private final String roleName;

        public AccountData(String username, String email, String password, String roleName) {
            this.username = username;
            this.email = email;
            this.password = password;
            this.roleName = roleName;
        }
    }
} 