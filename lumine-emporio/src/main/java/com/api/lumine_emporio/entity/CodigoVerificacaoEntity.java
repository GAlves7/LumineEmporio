package com.api.lumine_emporio.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class CodigoVerificacaoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String codigo;

	@Column(nullable = false)
	private LocalDateTime dataExpiracao;
	
	@OneToOne
	private UsuarioEntity usuario;

	public CodigoVerificacaoEntity(String codigo, String codigoVerificacao, LocalDateTime dataExpiracao,
			UsuarioEntity usuario) {
		this.codigo = codigo;
		this.dataExpiracao = dataExpiracao;
		this.usuario = usuario;
	}

	public CodigoVerificacaoEntity() {}

	
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
