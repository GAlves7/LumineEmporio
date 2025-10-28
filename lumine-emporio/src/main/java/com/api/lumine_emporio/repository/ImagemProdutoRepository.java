package com.api.lumine_emporio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.lumine_emporio.entity.ImagemProdutoEntity;

public interface ImagemProdutoRepository extends JpaRepository<ImagemProdutoEntity, Long>{
	boolean existsByUrl(String url);
	
	@Query("SELECT next_val FROM `tb_imagemproduto_seq`")
	Long pegarProxId();
}
