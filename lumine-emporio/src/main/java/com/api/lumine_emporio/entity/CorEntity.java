package com.api.lumine_emporio.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_cor")
public class CorEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id_cor")
	private Long idCor;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false, unique = true)
	private String codigoHex;
	
	
	//Relacionamentos
	@ManyToMany(mappedBy = "cores")
	private Set<ProdutoVariacaoEntity> produtos;

	public CorEntity(String nome, String codigoHex, Set<ProdutoVariacaoEntity> produtos) {
		this.nome = nome;
		this.codigoHex = codigoHex;
		this.produtos = produtos;
	}
	
	
	//Getters e Setters
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigoHex() {
		return codigoHex;
	}

	public void setCodigoHex(String codigoHex) {
		this.codigoHex = codigoHex;
	}

	public Set<ProdutoVariacaoEntity> getProdutos() {
		return produtos;
	}

	public void setProdutos(Set<ProdutoVariacaoEntity> produtos) {
		this.produtos = produtos;
	}

	public Long getIdCor() {
		return idCor;
	}
}
