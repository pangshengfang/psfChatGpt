package com.psf.core.persistence.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "permission")
@Data
public class PermissionEntity extends BaseEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "permission_name")
    private String permissionName;

    @Column(name = "type")
    private String type;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "openai_organization")
    private String organization;

    @Column(name = "openai_group")
    private String group;

    @Column(name = "allow_create_engine")
    private Boolean allowCreateEngine;

    @Column(name = "allow_sampling")
    private Boolean allowSampling;

    @Column(name = "allow_logprobs")
    private Boolean allowLogprobs;

    @Column(name = "allow_search_indices")
    private Boolean allowSearchIndices;

    @Column(name = "allow_view")
    private Boolean allowView;

    @Column(name = "allow_fine_tuning")
    private Boolean allowFineTuning;

    @Column(name = "is_blocking")
    private Boolean isBlocking;
}
