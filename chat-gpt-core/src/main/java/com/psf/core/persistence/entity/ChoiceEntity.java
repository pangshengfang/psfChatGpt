package com.psf.core.persistence.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "choice")
@Data
public class ChoiceEntity extends BaseEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "model_name")
    private String modelName;

    @Column(name = "message")
    private String message;

    @Column(name = "content")
    private String content;

    @Column(name = "c_index")
    private String cIndex;

    @Column(name = "finish_reason")
    private String finishReason;
}
