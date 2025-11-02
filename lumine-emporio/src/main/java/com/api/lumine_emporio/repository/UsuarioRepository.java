package com.api.lumine_emporio.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.api.lumine_emporio.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID>{
	Optional<UserDetails> findByEmail(String email);
	
	boolean existsByTelefoneOrEmail(String telefone, String email);
}
