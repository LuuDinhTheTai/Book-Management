package com.me.book_management.service.impl;

import com.me.book_management.entity.rbac0.Resource;
import com.me.book_management.repository.rbac0.ResourceRepository;
import com.me.book_management.service.ResourceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;

    @Override
    public Resource createIfNotExists(Resource resource) {
        log.info("(createIfNotExists) resource: {}", resource);

        Resource existingResource = findByName(resource.getName());
        if (existingResource != null) {
            return existingResource;
        }

        return resourceRepository.save(resource);
    }

    @Override
    public Resource findByName(String name) {
        log.info("(find) resource: {}", name);

        return resourceRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Resource not found"));
    }
}
