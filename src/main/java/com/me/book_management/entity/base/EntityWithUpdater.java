package com.me.book_management.entity.base;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class EntityWithUpdater {

    @CreatedDate
    private Date createdAt;
    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Date updatedAt;
    @CreatedBy
    private String updatedBy;

    private Date deletedAt;
    private String deletedBy;
}
