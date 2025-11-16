package com.api.lumine_emporio.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.lumine_emporio.entity.LinkVerificacaoEntity;
import com.api.lumine_emporio.entity.UsuarioEntity;
import com.api.lumine_emporio.entity.enums.UsuarioStatus;
import com.api.lumine_emporio.repository.LinkVerificacaoRepository;

import jakarta.transaction.Transactional;

@Service
public class LinkVerificacaoService {
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
		
		linkEntity.setUsuario(usuario);
		linkEntity.setLink(UUID.randomUUID().toString());
		linkEntity.setDataExpiracao(LocalDateTime.now().plusMinutes(5));
		
		linkVerificacaoRepository.save(linkEntity);
		
		mailService.enviarEmailTexto(usuario.getEmail(), "Email de Verificação;", "Link: http://localhost:8080/auth/verificar-link?token="
				+linkEntity.getLink()+"\nExpira em 5 minutos.");
		
		System.out.print("Link criado com sucesso: "+linkEntity.getLink());
	}
	
	public Optional<UsuarioEntity> consultarLink(String link) {
		Optional<LinkVerificacaoEntity> linkEntityOpt = linkVerificacaoRepository.findByLink(link);
		
		if (linkEntityOpt.isEmpty() || linkEntityOpt.get().getDataExpiracao().isBefore(LocalDateTime.now())) 
			return Optional.empty();
		return Optional.of(linkEntityOpt.get().getUsuario());
	}
	
	@Transactional
	public void deleteByUsuario(UsuarioEntity usuario) {
		linkVerificacaoRepository.deleteByUsuario(usuario);
	}
	
	
	
	
	
	
	
	
}
