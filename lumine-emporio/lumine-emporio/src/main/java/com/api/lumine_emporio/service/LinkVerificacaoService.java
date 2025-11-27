package com.api.lumine_emporio.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.lumine_emporio.entity.LinkVerificacaoEntity;
import com.api.lumine_emporio.entity.UsuarioEntity;
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
	    // 1. Busca direto
	    Optional<LinkVerificacaoEntity> linkOpt = linkVerificacaoRepository.findByUsuarioForUpdate(usuario);
	    
	    // 2. Se existe e não expirou → não criar novo
	    if (linkOpt.isPresent() && linkOpt.get().getDataExpiracao().isAfter(LocalDateTime.now())) {
	        return;
	    }
	    // 3. Se existe e expirou → remover e criar novo
	    if (linkOpt.isPresent()) {
	        linkVerificacaoRepository.delete(linkOpt.get());
	    }

	    // 4. Criar novo link
	    LinkVerificacaoEntity novo = new LinkVerificacaoEntity();
	    novo.setUsuario(usuario);
	    novo.setTokenId(UUID.randomUUID());
	    novo.setDataExpiracao(LocalDateTime.now().plusMinutes(5));

	    // Gerar token real
	    SecureRandom random = new SecureRandom();
	    byte[] bytes = new byte[32];
	    random.nextBytes(bytes);
	    String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

	    novo.setToken(passwordEncoder.encode(token));
	    try {
	        linkVerificacaoRepository.save(novo);
	        String linkFinal = "http://lumineemporio.store/recuperarSenha2.html?token=" + token +
	                "&tokenId=" + novo.getTokenId();
	        System.out.println("Link: "+linkFinal);
		    mailService.enviarEmailTexto(usuario.getEmail(), "Verificação", "<strong>Link:</strong> "+linkFinal);
	        
	    } catch (DataIntegrityViolationException e) {
	       System.out.println("Falha ao salvar link: "+e.getMessage());
	    }
	}
	
	public Optional<UsuarioEntity> consultarLink(UUID tokenId, String token) {
		Optional<LinkVerificacaoEntity> linkEntityOpt = linkVerificacaoRepository.findByTokenId(tokenId);
		
		//Se linkEntityOpt é nulo, ou esta expirado, ou o token está incorreto retorna nulo
		if (linkEntityOpt.isEmpty() || linkEntityOpt.get().getDataExpiracao().isBefore(LocalDateTime.now()) 
				|| !passwordEncoder.matches(token, linkEntityOpt.get().getToken())) {
			return Optional.empty();
		}
		
		return Optional.of(linkEntityOpt.get().getUsuario());
	}
	
	@Transactional
	public void deleteByUsuario(UsuarioEntity usuario) {
		linkVerificacaoRepository.deleteByUsuario(usuario);
	}
	
	
	
	
	
	
	
	
}
