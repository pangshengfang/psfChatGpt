package com.psf.core.persistence.dao;

import com.psf.core.persistence.entity.CronLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CronLogDao extends JpaRepository<CronLogEntity, Integer>, JpaSpecificationExecutor<CronLogEntity> {
}
