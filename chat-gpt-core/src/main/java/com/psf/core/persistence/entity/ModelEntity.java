package com.psf.core.persistence.entity;

import com.psf.core.persistence.converter.List2StringConverter;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "model")
@Data
public class ModelEntity extends BaseEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "model_name")
    private String modelName;

    @Column(name = "type")
    private String type;

    @Column(name = "root")
    private String root;

    @Column(name = "parent")
    private String parent;

    @Column(name = "owned_by")
    private String ownedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @Column(name = "permission_ids")
    @Convert(converter = List2StringConverter.class)
    private List<Integer> permissionIds;
}
