package com.api.lumine_emporio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.lumine_emporio.entity.ProdutoEntity;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long>{

}
