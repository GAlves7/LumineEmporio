package com.api.lumine_emporio.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.lumine_emporio.entity.ItemCarrinhoEntity;

import com.api.lumine_emporio.entity.ProdutoVariacaoEntity;
import com.api.lumine_emporio.entity.UsuarioEntity;



@Repository
public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinhoEntity, Long>{
	
	Page<ItemCarrinhoEntity> findAllByUsuario(UsuarioEntity usuarioEntity, Pageable pageable);
	
	boolean existsByUsuarioAndProdutoVariacao(UsuarioEntity usuario, ProdutoVariacaoEntity produtoVariacao);
	
	ItemCarrinhoEntity findByUsuarioAndProdutoVariacao(UsuarioEntity usuario, ProdutoVariacaoEntity produtoVariacao);
	
}
