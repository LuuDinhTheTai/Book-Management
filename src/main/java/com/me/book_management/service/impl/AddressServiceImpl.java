package com.me.book_management.service.impl;

import com.me.book_management.annotation.address.Create;
import com.me.book_management.annotation.address.Delete;
import com.me.book_management.annotation.address.Update;
import com.me.book_management.dto.request.account.address.CreateAddressRequest;
import com.me.book_management.dto.request.account.address.UpdateAddressRequest;
import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.account.Address;
import com.me.book_management.exception.NotFoundException;
import com.me.book_management.repository.account.AddressRepository;
import com.me.book_management.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    @Transactional
    @Create
    public Address create(CreateAddressRequest request, Account account) {
        log.info("(create) request: {}, account: {}", request, account);

        Address address = new Address();
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setPostalCode(request.getPostalCode());
        address.setIsDefault(request.getIsDefault());

        // If this is set as default, unset other default addresses
        if (request.getIsDefault()) {
            List<Address> existingAddresses = addressRepository.findByAccounts(account);
            for (Address existingAddress : existingAddresses) {
                if (existingAddress.getIsDefault()) {
                    existingAddress.setIsDefault(false);
                    addressRepository.save(existingAddress);
                }
            }
        }

        address.getAccounts().add(account);
        account.getAddresses().add(address);
        
        Address savedAddress = addressRepository.save(address);
        log.info("(create) address response: {}", savedAddress);
        
        return savedAddress;
    }

    @Override
    public Address find(Long id) {
        log.info("(find) id: {}", id);

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Address not found with id: " + id));

        log.info("(find) address response: {}", address);
        return address;
    }

    @Override
    public List<Address> findByAccount(Account account) {
        log.info("(findByAccount) account: {}", account);

        List<Address> addresses = addressRepository.findByAccounts(account);
        log.info("(findByAccount) addresses size: {}", addresses.size());
        return addresses;
    }

    @Override
    @Transactional
    @Update
    public Address update(Long id, UpdateAddressRequest request) {
        log.info("(update) id: {}, request: {}", id, request);

        Address address = find(id);
        
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setPostalCode(request.getPostalCode());
        address.setIsDefault(request.getIsDefault());

        // If this is set as default, unset other default addresses
        if (request.getIsDefault()) {
            List<Address> existingAddresses = addressRepository.findByAccounts(address.getAccounts().iterator().next());
            for (Address existingAddress : existingAddresses) {
                if (!existingAddress.getId().equals(id) && existingAddress.getIsDefault()) {
                    existingAddress.setIsDefault(false);
                    addressRepository.save(existingAddress);
                }
            }
        }

        Address updatedAddress = addressRepository.save(address);
        log.info("(update) address response: {}", updatedAddress);
        return updatedAddress;
    }

    @Override
    @Transactional
    @Delete
    public void delete(Long id) {
        log.info("(delete) id: {}", id);

        Address address = find(id);
        addressRepository.delete(address);
        
        log.info("(delete) address deleted successfully");
    }
} 