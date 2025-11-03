package com.api.lumine_emporio.service;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.lumine_emporio.entity.PasswordResetToken;
import com.api.lumine_emporio.entity.UsuarioEntity;
import com.api.lumine_emporio.exception.NaoEncontradoException;
import com.api.lumine_emporio.handler.PasswordResetTokenRepository;
import com.api.lumine_emporio.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private MailService mailService;

	
	public UsuarioEntity save(UsuarioEntity usuarioEntity) {
		return usuarioRepository.save(usuarioEntity);
	}
	
	public UsuarioEntity findByEmail(String email) {
		return (UsuarioEntity) usuarioRepository.findByEmail(email).orElseThrow(() -> new NaoEncontradoException("Usuario com email: "+email+" não encontrado."));
	}
	
	public PasswordResetToken criarTokenReset(UsuarioEntity usuario) {
		if(passwordResetTokenRepository.existsByUsuario(usuario)) {
			PasswordResetToken existentToken = passwordResetTokenRepository.findByUsuario(usuario);
			if(existentToken.getDataExpiracao().isAfter(LocalDateTime.now())) return existentToken;
		}
		
		
		PasswordResetToken passwordResetToken = new PasswordResetToken();
		
		passwordResetToken.setToken(UUID.randomUUID().toString());
		passwordResetToken.setCodigoVerificacao(String.format("%06d", new Random().nextInt(9999999)));
		passwordResetToken.setUsuario(usuario);
		passwordResetToken.setDataExpiracao(LocalDateTime.now().plusMinutes(15));
		passwordResetTokenRepository.save(passwordResetToken);
		mailService.enviarEmailTexto(usuario.getEmail(), 
				"Codigo de Verificacao Lumine Emporio.",
				"Codigo de Verificacao: "+passwordResetToken.getCodigoVerificacao()+".\nLink: http://localhost:8080/auth/verificar-link?token="
						+passwordResetToken.getToken()
						+"\nExpira em 15 minutos."
		);
		return passwordResetToken;
	}
	
	
	public Optional<String> getCodigoResetarSenha(UsuarioEntity usuario) {
		if(passwordResetTokenRepository.existsByUsuario(usuario)) {
			PasswordResetToken existentToken = passwordResetTokenRepository.findByUsuario(usuario);
			
			if(existentToken.getDataExpiracao().isAfter(LocalDateTime.now())) {
				return Optional.of(existentToken.getCodigoVerificacao());
			}
			
		}
		return Optional.empty();
	}
	
	
	public Optional<PasswordResetToken> findPasswordResetTokenByToken(String token) {
		if(passwordResetTokenRepository.existsByToken(token)) {
			PasswordResetToken existentToken = passwordResetTokenRepository.findByToken(token);
			if(existentToken.getDataExpiracao().isAfter(LocalDateTime.now())) return Optional.of(existentToken);
		}
		return Optional.empty();
	}
	
	public void deleteToken(PasswordResetToken passwordResetToken) {
		passwordResetTokenRepository.delete(passwordResetToken);
	}
	public void deleteTokenByUsuario(UsuarioEntity usuario) {
		passwordResetTokenRepository.delete(passwordResetTokenRepository.findByUsuario(usuario));
	}
}
