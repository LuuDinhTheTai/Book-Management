package com.me.book_management.service.impl;

import com.me.book_management.entity.rbac0.Resource;
import com.me.book_management.repository.rbac0.ResourceRepository;
import com.me.book_management.service.ResourceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;

    @Override
    public List<Resource> list() {
        return resourceRepository.findAll();
    }
}
