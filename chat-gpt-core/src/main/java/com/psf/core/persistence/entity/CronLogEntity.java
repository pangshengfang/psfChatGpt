package com.psf.core.persistence.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "cron_log")
@Data
public class CronLogEntity extends BaseEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "is_success")
    private Boolean isSuccess;

    @Column(name = "detail")
    private String detail;

    @Column(name = "duration")
    private Integer duration;
}
