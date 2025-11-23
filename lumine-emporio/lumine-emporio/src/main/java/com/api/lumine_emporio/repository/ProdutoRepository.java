package com.api.lumine_emporio.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.lumine_emporio.entity.ProdutoEntity;


public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long>{
	@Query("SELECT p FROM ProdutoEntity p " +
		       "WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :palavra, '%')) " +
		       "OR LOWER(p.descricao) LIKE LOWER(CONCAT('%', :palavra, '%'))")
	Page<ProdutoEntity> findByNomeOrDescricao(@Param("palavra") String palavra, Pageable pageable);
	
	
	Page<ProdutoEntity> findAll(Pageable pageable);
}
