package com.api.lumine_emporio.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class PasswordResetToken {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique = true)
	private String token;
	private String codigoVerificacao;
	private LocalDateTime dataExpiracao;
	
	@OneToOne
	private UsuarioEntity usuario;

	public PasswordResetToken(String token, String codigoVerificacao, LocalDateTime dataExpiracao,
			UsuarioEntity usuario) {
		this.token = token;
		this.codigoVerificacao = codigoVerificacao;
		this.dataExpiracao = dataExpiracao;
		this.usuario = usuario;
	}
	

	public PasswordResetToken() {}


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCodigoVerificacao() {
		return codigoVerificacao;
	}

	public void setCodigoVerificacao(String codigoVerificacao) {
		this.codigoVerificacao = codigoVerificacao;
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
