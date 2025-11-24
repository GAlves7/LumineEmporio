package com.api.lumine_emporio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.lumine_emporio.entity.ImagemProdutoEntity;
import java.util.List;
import com.api.lumine_emporio.entity.ProdutoEntity;


@Repository
public interface ImagemProdutoRepository extends JpaRepository<ImagemProdutoEntity, Long>{
	boolean existsByUrl(String url);
	
	List<ImagemProdutoEntity> findAllByProduto(ProdutoEntity produto);
	
}
