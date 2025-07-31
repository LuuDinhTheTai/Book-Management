package com.me.book_management.seeder;

import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.account.Address;
import com.me.book_management.repository.account.AccountRepository;
import com.me.book_management.repository.account.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AddressDataSeeder implements CommandLineRunner {

    private final AddressRepository addressRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public void run(String... args) {
        System.out.println("AddressDataSeeder: Starting address seeding...");
        seedAddresses();
        System.out.println("AddressDataSeeder: Completed address seeding.");
    }

    private void seedAddresses() {
        List<Account> accounts = accountRepository.findAll();
        
        if (accounts.isEmpty()) {
            return;
        }
        
        // Create sample addresses for each account
        for (Account account : accounts) {
            seedAddressesForAccount(account);
        }
    }
    
    private void seedAddressesForAccount(Account account) {
        List<AddressData> addressesToSeed = Arrays.asList(
            new AddressData(
                "123 Main Street", 
                "New York", 
                "NY", 
                "USA", 
                "10001", 
                true
            ),
            new AddressData(
                "456 Business Ave", 
                "New York", 
                "NY", 
                "USA", 
                "10002", 
                false
            ),
            new AddressData(
                "789 Shipping Lane", 
                "Los Angeles", 
                "CA", 
                "USA", 
                "90210", 
                false
            )
        );
        
        for (AddressData addressData : addressesToSeed) {
            if (!addressRepository.existsByStreetAndCityAndStateAndCountryAndPostalCode(
                    addressData.street, addressData.city, addressData.state, 
                    addressData.country, addressData.postalCode)) {
                
                Address address = createAddress(addressData);
                address.getAccounts().add(account);
                account.getAddresses().add(address);
                addressRepository.save(address);
            }
        }
        
        accountRepository.save(account);
    }
    
    private Address createAddress(AddressData addressData) {
        Address address = new Address();
        address.setStreet(addressData.street);
        address.setCity(addressData.city);
        address.setState(addressData.state);
        address.setCountry(addressData.country);
        address.setPostalCode(addressData.postalCode);
        address.setIsDefault(addressData.isDefault);
        return address;
    }
    
    private static class AddressData {
        private final String street;
        private final String city;
        private final String state;
        private final String country;
        private final String postalCode;
        private final Boolean isDefault;
        
        public AddressData(String street, String city, String state, String country, 
                         String postalCode, Boolean isDefault) {
            this.street = street;
            this.city = city;
            this.state = state;
            this.country = country;
            this.postalCode = postalCode;
            this.isDefault = isDefault;
        }
    }
} 