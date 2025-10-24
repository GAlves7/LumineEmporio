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
@Table(name = "tb_marca")
public class MarcaEntity {
	@Id
	@Column(name = "id_marca")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long idMarca;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String descricao;
	
	
	//Relacionamentos
	@ManyToMany(mappedBy = "id_marca")
	private Set<ProdutoEntity> produtos;

	public MarcaEntity(String nome, String descricao, Set<ProdutoEntity> produtos) {
		this.nome = nome;
		this.descricao = descricao;
		this.produtos = produtos;
	}
	
	
	//Getters e Setters
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Set<ProdutoEntity> getProdutos() {
		return produtos;
	}

	public void setProdutos(Set<ProdutoEntity> produtos) {
		this.produtos = produtos;
	}

	public Long getIdMarca() {
		return idMarca;
	}
}
