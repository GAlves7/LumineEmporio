package com.api.lumine_emporio.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.lumine_emporio.entity.ProdutoEntity;
import java.util.List;
import com.api.lumine_emporio.entity.CategoriaEntity;



public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long>{
	@Query("""
		    SELECT p FROM ProdutoEntity p
		    WHERE LOWER(p.nome) LIKE :texto
		       OR LOWER(p.descricao) LIKE :texto
		""")
	Page<ProdutoEntity> findByTexto(@Param("texto") String texto, Pageable pageable);
	
	@Query("""
		    SELECT DISTINCT p FROM ProdutoEntity p
		    JOIN p.categorias c
		    WHERE c IN :categorias
		      AND (LOWER(p.nome) LIKE :texto OR LOWER(p.descricao) LIKE :texto)
		""")
	Page<ProdutoEntity> findByCategoriasInAndTexto(
	        @Param("categorias") List<CategoriaEntity> categorias,
	        @Param("texto") String texto,
	        Pageable pageable
	);
	
	
	Page<ProdutoEntity> findAll(Pageable pageable);
	
	Page<ProdutoEntity> findByCategoriasIn(List<CategoriaEntity> categorias, Pageable pageable);
	
}
