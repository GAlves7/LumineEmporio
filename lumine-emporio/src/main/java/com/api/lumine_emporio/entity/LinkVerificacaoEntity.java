package com.api.lumine_emporio.entity;

import java.time.LocalDateTime;

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
	
	@Column(unique = true, nullable = false)
	private String link;
	
	@Column(nullable = false)
	private LocalDateTime dataExpiracao;
	
	@OneToOne
	private UsuarioEntity usuario;

	
	public LinkVerificacaoEntity(String link, LocalDateTime dataExpiracao, UsuarioEntity usuario) {
		this.link = link;
		this.dataExpiracao = dataExpiracao;
		this.usuario = usuario;
	}

	public LinkVerificacaoEntity() {}



	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
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
