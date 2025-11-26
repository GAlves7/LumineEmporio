package com.api.lumine_emporio.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.api.lumine_emporio.dtos.AtualizarPerfilDTO;
import com.api.lumine_emporio.entity.UsuarioEntity;
import com.api.lumine_emporio.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class PerfilService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public UsuarioEntity atualizarPerfil(String email, AtualizarPerfilDTO dto) {
		Optional<UsuarioEntity> usuario = usuarioRepository.findUsuarioEntityByEmail(email);
		if (!usuario.isPresent()) {
			throw new RuntimeException("Usuário não encontrado");
		}
		
		UsuarioEntity usuarioAtualizado = usuario.get();
		
		if (dto.getNovaSenha() != null && !dto.getNovaSenha().isEmpty()) {
			if (dto.getConfirmarSenha() == null || dto.getConfirmarSenha().isEmpty()) {
				throw new RuntimeException("Confirmação de senha é obrigatória");
			}
			
			if (!dto.getNovaSenha().equals(dto.getConfirmarSenha())) {
				throw new RuntimeException("As senhas não coincidem");
			}
			
			if (dto.getNovaSenha().length() < 6) {
				throw new RuntimeException("A senha deve ter no mínimo 6 caracteres");
			}
			
			usuarioAtualizado.setPassword(passwordEncoder.encode(dto.getNovaSenha()));
		}
		
		if (dto.getNome() != null && !dto.getNome().isEmpty()) {
			usuarioAtualizado.setNome(dto.getNome());
		}
		
		if (dto.getTelefone() != null && !dto.getTelefone().isEmpty()) {
			usuarioAtualizado.setTelefone(dto.getTelefone());
		}
		
		if (dto.getEmail() != null && !dto.getEmail().isEmpty() && !dto.getEmail().equals(email)) {
			// Verifica se o novo email já existe
			Optional<UsuarioEntity> emailExistente = usuarioRepository.findUsuarioEntityByEmail(dto.getEmail());
			if (emailExistente.isPresent()) {
				throw new RuntimeException("Email já está em uso");
			}
			usuarioAtualizado.setEmail(dto.getEmail());
		}
		
		return usuarioRepository.save(usuarioAtualizado);
	}
	
	public UsuarioEntity obterPerfil(String email) {
		Optional<UsuarioEntity> usuario = usuarioRepository.findUsuarioEntityByEmail(email);
		if (!usuario.isPresent()) {
			throw new RuntimeException("Usuário não encontrado");
		}
		return usuario.get();
	}
}