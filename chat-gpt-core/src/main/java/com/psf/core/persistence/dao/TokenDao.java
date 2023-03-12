package com.psf.core.persistence.dao;

import com.psf.core.persistence.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenDao extends JpaRepository<TokenEntity, Integer> {
}
