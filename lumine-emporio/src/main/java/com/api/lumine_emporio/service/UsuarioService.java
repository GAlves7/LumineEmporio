package com.api.lumine_emporio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api.lumine_emporio.entity.UsuarioEntity;
import com.api.lumine_emporio.exception.NaoEncontradoException;
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
	
	public UsuarioEntity findByEmail(String email) {
		return (UsuarioEntity) usuarioRepository.findByEmail(email).orElseThrow(() -> new NaoEncontradoException("Usuario com email: "+email+" não encontrado."));
	}
	
	
}
