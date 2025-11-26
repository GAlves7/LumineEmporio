package com.api.lumine_emporio.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class LinkVerificacaoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private UUID tokenId;
	
	@Column(unique = true, nullable = false)
	private String token;
	
	@Column(nullable = false)
	private LocalDateTime dataExpiracao;
	
	@OneToOne
	private UsuarioEntity usuario;

	

	public LinkVerificacaoEntity(UUID tokenId, String token, LocalDateTime dataExpiracao, UsuarioEntity usuario) {
		this.tokenId = tokenId;
		this.token = token;
		this.dataExpiracao = dataExpiracao;
		this.usuario = usuario;
	}
	public LinkVerificacaoEntity() {}
	
	
	public UUID getTokenId() {
		return tokenId;
	}
	public void setTokenId(UUID tokenId) {
		this.tokenId = tokenId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public LocalDateTime getDataExpiracao() {
		return dataExpiracao;
	}
	public void setDataExpiracao(LocalDateTime dataExpiracao) {
		this.dataExpiracao = dataExpiracao;
	}
	public UsuarioEntity getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioEntity usuario) {
		this.usuario = usuario;
	}
	public Long getId() {
		return id;
	}
}
