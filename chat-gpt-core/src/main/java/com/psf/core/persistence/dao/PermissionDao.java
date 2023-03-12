package com.psf.core.persistence.dao;

import com.psf.core.persistence.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PermissionDao extends JpaRepository<PermissionEntity, Integer>, JpaSpecificationExecutor<PermissionEntity> {
}
