package com.me.book_management.repository.account;

import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.account.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    boolean existsByStreetAndCityAndStateAndCountryAndPostalCode(
            String street, String city, String state, String country, String postalCode);
} 