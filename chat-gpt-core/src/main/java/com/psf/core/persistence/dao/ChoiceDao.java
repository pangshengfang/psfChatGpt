package com.psf.core.persistence.dao;

import com.psf.core.persistence.entity.ChoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChoiceDao extends JpaRepository<ChoiceEntity, Integer>, JpaSpecificationExecutor<ChoiceEntity> {
}
