package com.api.lumine_emporio.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.lumine_emporio.entity.LinkVerificacaoEntity;
import com.api.lumine_emporio.entity.UsuarioEntity;
import com.api.lumine_emporio.entity.enums.UsuarioStatus;
import com.api.lumine_emporio.repository.LinkVerificacaoRepository;

import jakarta.transaction.Transactional;

@Service
public class LinkVerificacaoService {
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
	private LinkVerificacaoRepository linkVerificacaoRepository;
	@Autowired
	private MailService mailService;

	
	@Transactional
	public void criarLink(UsuarioEntity usuario) {
		if (usuario.getStatus().equals(UsuarioStatus.P)) return;
		if (linkVerificacaoRepository.existsByUsuario(usuario)) {
			LinkVerificacaoEntity existentLink = linkVerificacaoRepository.findByUsuario(usuario);
			if (existentLink.getDataExpiracao().isAfter(LocalDateTime.now())) return;
			linkVerificacaoRepository.delete(existentLink);
		}
		
		LinkVerificacaoEntity linkEntity = new LinkVerificacaoEntity();
		
		//Geração do token
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[32];
		random.nextBytes(bytes);
		String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
		
		
		linkEntity.setUsuario(usuario);
		linkEntity.setTokenId(UUID.randomUUID());
		linkEntity.setToken(passwordEncoder.encode(token));
		linkEntity.setDataExpiracao(LocalDateTime.now().plusMinutes(5));
		
		linkVerificacaoRepository.save(linkEntity);
		
		String link = "Link: http://localhost:8080/auth/verificar-link?token="
				+token+"&tokenId="+linkEntity.getTokenId()
				+ "\nExpira em 5 minutos.";
		
		mailService.enviarEmailTexto(usuario.getEmail(), "Email de Verificação;", link);
		
		System.out.println("Link criado com sucesso: "+link);
		System.out.println(token);
	}
	
	public Optional<UsuarioEntity> consultarLink(UUID tokenId, String token) {
		Optional<LinkVerificacaoEntity> linkEntityOpt = linkVerificacaoRepository.findByTokenId(tokenId);
		
		//Se linkEntityOpt é nulo, ou esta expirado, ou o token está incorreto retorna nulo
		if (linkEntityOpt.isEmpty() || linkEntityOpt.get().getDataExpiracao().isBefore(LocalDateTime.now()) 
				|| !passwordEncoder.matches(token, linkEntityOpt.get().getToken())) {
			System.out.println("Token:"+token);
			return Optional.empty();
		}
		
		return Optional.of(linkEntityOpt.get().getUsuario());
	}
	
	@Transactional
	public void deleteByUsuario(UsuarioEntity usuario) {
		linkVerificacaoRepository.deleteByUsuario(usuario);
	}
	
	
	
	
	
	
	
	
}
