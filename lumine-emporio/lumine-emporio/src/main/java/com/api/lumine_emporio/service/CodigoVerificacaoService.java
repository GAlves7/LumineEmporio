package com.api.lumine_emporio.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.lumine_emporio.entity.CodigoVerificacaoEntity;
import com.api.lumine_emporio.repository.CodigoVerificacaoRepository;

import jakarta.transaction.Transactional;

@Service
public class CodigoVerificacaoService{
	@Autowired
	private CodigoVerificacaoRepository codigoVerificacaoRepository;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public CodigoVerificacaoEntity save(CodigoVerificacaoEntity codigoVerificacaoEntity) {
		return codigoVerificacaoRepository.save(codigoVerificacaoEntity);
	}
	
	@Transactional
	public void criarCodigo(String email){
		Optional<CodigoVerificacaoEntity> existentCodigo = codigoVerificacaoRepository.findByEmail(email);
			if (existentCodigo.isPresent()) {
				if(existentCodigo.get().getDataExpiracao().isAfter(LocalDateTime.now())) return;
				codigoVerificacaoRepository.delete(existentCodigo.get());
			}
		
		
		CodigoVerificacaoEntity codigoEntity = new CodigoVerificacaoEntity();
		String codigo = String.format("%06d", new SecureRandom().nextInt(999999));
		
		codigoEntity.setHashCodigo(passwordEncoder.encode(codigo));
		codigoEntity.setDataExpiracao(LocalDateTime.now().plusMinutes(5));
		codigoEntity.setEmail(email);
		codigoVerificacaoRepository.save(codigoEntity);
		
		mailService.enviarEmailTexto(email, "Email de verificação.", "<strong>Codigo de Verificação:</strong> "+codigo);
		
		System.out.print("Codigo de Verificação: "+codigo);
	}
	
	
	public Boolean compararCodigo(String email, String codigo) {
		Optional<CodigoVerificacaoEntity> codigoEntityOpt = codigoVerificacaoRepository.findByEmail(email);
		
		if (codigoEntityOpt.isEmpty() || codigoEntityOpt.get().getDataExpiracao().isBefore(LocalDateTime.now()) 
				|| !passwordEncoder.matches(codigo, codigoEntityOpt.get().getHashCodigo())) 
			return false;
				
		return true;
	}
	
	@Transactional
	public void deletebyEmail(String email) {
		codigoVerificacaoRepository.deleteByEmail(email);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
