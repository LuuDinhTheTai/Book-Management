package com.me.book_management.repository.rbac0;

import com.me.book_management.entity.rbac0.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {
}
