package com.api.lumine_emporio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.lumine_emporio.entity.CodigoVerificacaoEntity;
import com.api.lumine_emporio.entity.UsuarioEntity;

@Repository
public interface CodigoVerificacaoRepository extends JpaRepository<CodigoVerificacaoEntity, Long>{
	boolean existsByUsuario(UsuarioEntity usuario);
	CodigoVerificacaoEntity findByUsuario(UsuarioEntity usuario);
	void deleteByUsuario(UsuarioEntity usuario);
}

