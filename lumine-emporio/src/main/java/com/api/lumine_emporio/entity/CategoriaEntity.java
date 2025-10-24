package com.api.lumine_emporio.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_categoria")
public class CategoriaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_categoria")
	private Long idCategoria;
	
	@Column(nullable=false, unique = true, length = 35)
	private String nome;
	
	@Column(nullable=false)
	private String descricao;
	
	
	//Relacionamentos
	@OneToMany(mappedBy = "id_categoria")
	private Set<SubCategoriaEntity> subCategorias;
	
	@ManyToMany(mappedBy = "id_categoria")
	private Set<ProdutoEntity> produtos;

	public CategoriaEntity(String nome, String descricao, Set<SubCategoriaEntity> subCategorias, Set<ProdutoEntity> produtos) {
		this.nome = nome;
		this.descricao = descricao;
		this.subCategorias = subCategorias;
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

	public Set<SubCategoriaEntity> getSubCategorias() {
		return subCategorias;
	}

	public void setSubCategorias(Set<SubCategoriaEntity> subCategorias) {
		this.subCategorias = subCategorias;
	}

	public Set<ProdutoEntity> getProdutos() {
		return produtos;
	}

	public void setProdutos(Set<ProdutoEntity> produtos) {
		this.produtos = produtos;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}
}
