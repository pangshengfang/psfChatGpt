package com.psf.core.persistence.dao;

import com.psf.core.persistence.entity.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ModelDao extends JpaRepository<ModelEntity, Integer>, JpaSpecificationExecutor<ModelEntity> {
}
