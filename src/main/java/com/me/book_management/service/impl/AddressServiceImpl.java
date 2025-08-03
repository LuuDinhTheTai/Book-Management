package com.me.book_management.service.impl;

import com.me.book_management.dto.request.account.address.CreateAddressRequest;
import com.me.book_management.dto.request.account.address.UpdateAddressRequest;
import com.me.book_management.entity.account.Account;
import com.me.book_management.entity.account.Address;
import com.me.book_management.exception.ForbiddenException;
import com.me.book_management.exception.NotFoundException;
import com.me.book_management.repository.account.AccountRepository;
import com.me.book_management.repository.account.AddressRepository;
import com.me.book_management.service.AddressService;
import com.me.book_management.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AccountRepository accountRepository;

    @Override
    public Address create(CreateAddressRequest request) {
        log.info("(create) request: {}", request);

        Account account = accountRepository.findByUsername(CommonUtil.getCurrentAccount())
                .orElseThrow(() -> new NotFoundException("Account not found: " + CommonUtil.getCurrentAccount()));

        Address address = new Address();
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setPostalCode(request.getPostalCode());
        address.setAccount(account);

        account.getAddresses().add(address);
        
        Address savedAddress = addressRepository.save(address);
        accountRepository.save(account);
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
    public List<Address> list() {
        Account account = accountRepository.findByUsername(CommonUtil.getCurrentAccount())
                .orElseThrow(() -> new NotFoundException("Account not found with username: " + CommonUtil.getCurrentAccount()));

        return account.getAddresses();
    }

    @Override
    public Address update(Long id, UpdateAddressRequest request) {
        log.info("(update) id: {}, request: {}", id, request);

        Account account = accountRepository.findByUsername(CommonUtil.getCurrentAccount())
                .orElseThrow(() -> new NotFoundException("Account not found with username: " + CommonUtil.getCurrentAccount()));

        Address address = find(id);

        if (!address.getAccount().getUsername().equals(account.getUsername())) {
            throw new ForbiddenException("You are not owner");
        }

        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setPostalCode(request.getPostalCode());

        Address updatedAddress = addressRepository.save(address);
        log.info("(update) address response: {}", updatedAddress);
        return updatedAddress;
    }

    @Override
    public void delete(Long id) {

        addressRepository.deleteById(id);
    }
} 