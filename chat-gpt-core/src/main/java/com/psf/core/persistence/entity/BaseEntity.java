package com.psf.core.persistence.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public abstract class BaseEntity {
    @Column(name = "create_time", nullable = false)
    protected LocalDateTime createTime;

    @Column(name = "update_time", nullable = false)
    protected LocalDateTime updateTime;

    @PrePersist
    protected void prePersist() {
        if (this.createTime == null) {
            this.createTime = LocalDateTime.now();
        }
        if (this.updateTime == null) {
            this.updateTime = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void preUpdate() {
        this.updateTime = LocalDateTime.now();
    }
}
