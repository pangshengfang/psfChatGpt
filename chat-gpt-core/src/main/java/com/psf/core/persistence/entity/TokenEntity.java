package com.psf.core.persistence.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "token")
@Data
public class TokenEntity extends BaseEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "token")
    private String token;
}
