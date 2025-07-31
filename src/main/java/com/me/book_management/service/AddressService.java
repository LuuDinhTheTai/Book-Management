package com.me.book_management.service;

import com.me.book_management.dto.request.account.address.CreateAddressRequest;
import com.me.book_management.dto.request.account.address.UpdateAddressRequest;
import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.account.Address;

import java.util.List;

public interface AddressService {

    Address create(CreateAddressRequest request);

    Address find(Long id);

    List<Address> list();

    Address update(Long id, UpdateAddressRequest request);

    void delete(Long id);
} 