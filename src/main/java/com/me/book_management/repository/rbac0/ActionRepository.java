package com.me.book_management.repository.rbac0;

import com.me.book_management.entity.rbac0.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActionRepository extends JpaRepository<Action,Long> {

    Optional<Action> findByName(String name);
}
