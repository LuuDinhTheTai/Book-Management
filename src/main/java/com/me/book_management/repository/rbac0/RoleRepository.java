package com.me.book_management.repository.rbac0;

import com.me.book_management.entity.rbac0.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByName(String name);
}
