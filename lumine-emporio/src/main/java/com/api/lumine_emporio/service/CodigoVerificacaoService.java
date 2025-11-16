package com.api.lumine_emporio.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.lumine_emporio.entity.CodigoVerificacaoEntity;
import com.api.lumine_emporio.entity.UsuarioEntity;
import com.api.lumine_emporio.entity.enums.UsuarioStatus;
import com.api.lumine_emporio.repository.CodigoVerificacaoRepository;

import jakarta.transaction.Transactional;

@Service
public class CodigoVerificacaoService{
	@Autowired
	private CodigoVerificacaoRepository codigoVerificacaoRepository;
	
	@Autowired
	private MailService mailService;
	
	@Transactional
	public CodigoVerificacaoEntity save(CodigoVerificacaoEntity codigoVerificacaoEntity) {
		return codigoVerificacaoRepository.save(codigoVerificacaoEntity);
	}
	
	@Transactional
	public void criarCodigo(UsuarioEntity usuario){
		if (!usuario.getStatus().equals(UsuarioStatus.P)) return;
		
		if (codigoVerificacaoRepository.existsByUsuario(usuario)) {
			CodigoVerificacaoEntity existentCodigo = codigoVerificacaoRepository.findByUsuario(usuario);
			if (existentCodigo.getDataExpiracao().isAfter(LocalDateTime.now())) return;
			codigoVerificacaoRepository.delete(existentCodigo);
		}
		
		CodigoVerificacaoEntity codigoEntity = new CodigoVerificacaoEntity();
		codigoEntity.setCodigo(String.format("%06d", new SecureRandom().nextInt(999999)));
		codigoEntity.setUsuario(usuario);
		codigoEntity.setDataExpiracao(LocalDateTime.now().plusMinutes(5));
		codigoVerificacaoRepository.save(codigoEntity);
		
		mailService.enviarEmailTexto(usuario.getEmail(), "Email de verificação.", "Link de Verificação: "+codigoEntity.getCodigo());
		System.out.print("Codigo de Verificação: "+codigoEntity.getCodigo());
		
		return;
	}
	
	
	public Boolean compararCodigo(UsuarioEntity usuario, String codigo) {
		CodigoVerificacaoEntity codigoEntity = codigoVerificacaoRepository.findByUsuario(usuario);
		if (codigoEntity.getDataExpiracao().isBefore(LocalDateTime.now())) throw new RuntimeException("Codigo expirado.");
				
		return codigoEntity.getCodigo().equals(codigo);
	}
	
	@Transactional
	public void deletebyUsuario(UsuarioEntity usuario) {
		codigoVerificacaoRepository.deleteByUsuario(usuario);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
