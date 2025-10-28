package com.api.lumine_emporio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.lumine_emporio.entity.ProdutoVariacaoEntity;


@Repository
public interface ProdutoVariacaoRepository extends JpaRepository<ProdutoVariacaoEntity, Long>{
	
	List<ProdutoVariacaoEntity> findByPromocaoIsNotNull();
}
