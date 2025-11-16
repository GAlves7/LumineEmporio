package com.api.lumine_emporio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.lumine_emporio.entity.LinkVerificacaoEntity;
import com.api.lumine_emporio.entity.UsuarioEntity;



@Repository
public interface LinkVerificacaoRepository extends JpaRepository<LinkVerificacaoEntity, Long>{
	boolean existsByUsuario(UsuarioEntity usuario);
	
	LinkVerificacaoEntity findByUsuario(UsuarioEntity usuario);
	Optional<LinkVerificacaoEntity> findByLink(String link);
	
	void deleteByUsuario(UsuarioEntity usuario);
}
