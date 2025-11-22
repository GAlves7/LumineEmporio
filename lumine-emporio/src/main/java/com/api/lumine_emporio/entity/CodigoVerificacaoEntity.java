package com.api.lumine_emporio.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CodigoVerificacaoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, name = "hash_codigo")
	private String hashCodigo;

	@Column(nullable = false)
	private LocalDateTime dataExpiracao;
	
	@Column(nullable = false, unique = true, length = 40)
	private String email;
	
	
	public CodigoVerificacaoEntity(String hashCodigo, LocalDateTime dataExpiracao, String email) {
		this.hashCodigo = hashCodigo;
		this.dataExpiracao = dataExpiracao;
		this.email = email;
	}

	public CodigoVerificacaoEntity() {}
	
	
	
	public String getHashCodigo() {
		return hashCodigo;
	}

	public void setHashCodigo(String hashCodigo) {
		this.hashCodigo = hashCodigo;
	}

	public LocalDateTime getDataExpiracao() {
		return dataExpiracao;
	}

	public void setDataExpiracao(LocalDateTime dataExpiracao) {
		this.dataExpiracao = dataExpiracao;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}
}
