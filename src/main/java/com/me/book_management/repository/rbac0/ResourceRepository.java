package com.me.book_management.repository.rbac0;

import com.me.book_management.entity.rbac0.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource,Long> {

    Optional<Resource> findByName(String name);
    boolean existsByName(String name);
}
