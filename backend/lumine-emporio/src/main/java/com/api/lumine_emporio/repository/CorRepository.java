package com.api.lumine_emporio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.lumine_emporio.entity.CorEntity;

public interface CorRepository extends JpaRepository<CorEntity, Long>{
	boolean existsByCodigoHex(String codigoHex);
}
