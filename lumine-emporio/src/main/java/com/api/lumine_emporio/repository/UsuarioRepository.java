package com.api.lumine_emporio.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.lumine_emporio.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID>{
	
}
