package com.api.lumine_emporio.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api.lumine_emporio.entity.UsuarioEntity;
import com.api.lumine_emporio.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	

	@Transactional
	public UsuarioEntity save(UsuarioEntity usuarioEntity) {
		return usuarioRepository.save(usuarioEntity);
	}
	
	public Optional<UsuarioEntity> findByEmail(String email) {
		return usuarioRepository.findUsuarioEntityByEmail(email);
	}
	
	
}
