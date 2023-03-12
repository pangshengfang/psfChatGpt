package com.psf.core.persistence.dao;

import com.psf.core.persistence.entity.CompletionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CompletionDao extends JpaRepository<CompletionEntity, Integer>, JpaSpecificationExecutor<CompletionEntity> {
}
