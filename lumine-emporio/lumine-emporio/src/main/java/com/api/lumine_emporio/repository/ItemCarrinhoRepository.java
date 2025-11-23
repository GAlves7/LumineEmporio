package com.api.lumine_emporio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.lumine_emporio.entity.ItemCarrinhoEntity;
import com.api.lumine_emporio.entity.UsuarioEntity;

@Repository
public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinhoEntity, Long> {
	List<ItemCarrinhoEntity> findByUsuario(UsuarioEntity usuario);
	Optional<ItemCarrinhoEntity> findByUsuarioAndIdItemCarrinho(UsuarioEntity usuario, Long idItemCarrinho);
	void deleteByUsuario(UsuarioEntity usuario);
}