package com.api.lumine_emporio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.lumine_emporio.entity.ProdutoEntity;


public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long>{
	@Query("SELECT p FROM ProdutoEntity p " +
		       "WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :palavra, '%')) " +
		       "   OR LOWER(p.descricao) LIKE LOWER(CONCAT('%', :palavra, '%'))")
	List<ProdutoEntity> findByNomeOrDescricao(@Param("palavra") String palavra);
}
