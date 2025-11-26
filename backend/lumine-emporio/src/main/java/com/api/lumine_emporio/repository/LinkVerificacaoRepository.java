package com.api.lumine_emporio.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.lumine_emporio.entity.LinkVerificacaoEntity;
import com.api.lumine_emporio.entity.UsuarioEntity;

import jakarta.persistence.LockModeType;



@Repository
public interface LinkVerificacaoRepository extends JpaRepository<LinkVerificacaoEntity, Long>{
	
	Optional<LinkVerificacaoEntity> findByUsuario(UsuarioEntity usuario);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT l FROM LinkVerificacaoEntity l WHERE l.usuario = :usuario")
	Optional<LinkVerificacaoEntity> findByUsuarioForUpdate(@Param("usuario") UsuarioEntity usuario);
	
	Optional<LinkVerificacaoEntity> findByTokenId(UUID tokenId);
	
	void deleteByUsuario(UsuarioEntity usuario);
}
