package com.me.book_management.entity.rbac0;

import jakarta.persistence.*;
import lombok.*;

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
    @ManyToOne
    private Action action;
    @ManyToOne
    private Resource resource;

    public Permission(String name, Action action, Resource resource) {
        this.name = name;
        this.action = action;
        this.resource = resource;
    }
}
