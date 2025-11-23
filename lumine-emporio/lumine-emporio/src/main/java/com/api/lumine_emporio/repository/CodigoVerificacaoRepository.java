package com.api.lumine_emporio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.lumine_emporio.entity.CodigoVerificacaoEntity;

@Repository
public interface CodigoVerificacaoRepository extends JpaRepository<CodigoVerificacaoEntity, Long>{
	Optional<CodigoVerificacaoEntity> findByEmail(String email);
	void deleteByEmail(String email);
}

