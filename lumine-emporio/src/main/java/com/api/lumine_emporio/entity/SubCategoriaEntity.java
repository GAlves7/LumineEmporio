package com.api.lumine_emporio.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_subcategoria")
public class SubCategoriaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id_subcategoria")
	private Long idSubCategoria;
	
	@Column(nullable=false, unique = true, length = 35)
	private String nome;
	
	@Column(nullable=false)
	private String descricao;
	
	
	//Relacionamentos
	@ManyToOne
	private CategoriaEntity categoria;
	
	@ManyToMany(mappedBy = "id_subcategoria")
	private Set<ProdutoEntity> produtos;

	
	public SubCategoriaEntity(String nome, String descricao, CategoriaEntity categoria, Set<ProdutoEntity> produtos) {
		this.nome = nome;
		this.descricao = descricao;
		this.categoria = categoria;
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

	public CategoriaEntity getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaEntity categoria) {
		this.categoria = categoria;
	}

	public Set<ProdutoEntity> getProdutos() {
		return produtos;
	}

	public void setProdutos(Set<ProdutoEntity> produtos) {
		this.produtos = produtos;
	}

	public Long getIdSubCategoria() {
		return idSubCategoria;
	}
}
