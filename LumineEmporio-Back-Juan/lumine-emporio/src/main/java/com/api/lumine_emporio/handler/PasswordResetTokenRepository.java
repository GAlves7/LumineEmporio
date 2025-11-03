package com.api.lumine_emporio.handler;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.lumine_emporio.entity.PasswordResetToken;
import com.api.lumine_emporio.entity.UsuarioEntity;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>{
	PasswordResetToken findByUsuario(UsuarioEntity usuario);
	PasswordResetToken findByToken(String token);
	boolean existsByUsuario(UsuarioEntity usuario);
	boolean existsByToken(String token);
}
