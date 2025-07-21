package com.me.book_management.entity.rbac0;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "permission")
    private Set<Action> actions = new HashSet<>();
    @OneToMany(mappedBy = "permission")
    private Set<Resource> resources = new HashSet<>();

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();
}
