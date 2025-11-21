package com.api.lumine_emporio.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_produto")
public class ProdutoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_produto")
	private Long idProduto;
	
	@Column(nullable=false)
	private String nome;
	
	@Column(nullable=false)
	private String descricao;
	
	@Column(nullable = false, precision = 2)
	private double preco;
	
	
	//Relacionamentos
	@ManyToMany
	@JoinTable(
			joinColumns = @JoinColumn(name = "id_produto"),
			inverseJoinColumns = @JoinColumn(name = "id_categoria")
	)
	@JsonBackReference
	private Set<CategoriaEntity> categorias;
	
	@ManyToMany
	@JoinTable(
			joinColumns = @JoinColumn(name = "id_produto"),
			inverseJoinColumns = @JoinColumn(name = "id_subcategoria")
	)
	private Set<SubCategoriaEntity> subCategoria;
	
	@OneToMany(mappedBy = "produto")
	@JsonManagedReference
	private Set<ImagemProdutoEntity> imagemProduto;
	
	@OneToMany(mappedBy = "produto")
	@JsonManagedReference
	private Set<ProdutoVariacaoEntity> produtoVariacao;
	
	@ManyToMany
	@JoinTable(
			name = "produto_marca",
			joinColumns = @JoinColumn(name = "id_produto"),
			inverseJoinColumns = @JoinColumn(name = "id_marca")
	)
	private Set<MarcaEntity> marcas;
	
	@OneToMany(mappedBy = "produto")
	private Set<AvaliacaoEntity> avaliacoes;
	
	
	public ProdutoEntity(String nome, String descricao, double preco) {
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
	}
	

	public ProdutoEntity() {}


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

	public Set<CategoriaEntity> getCategorias() {
		return categorias;
	}

	public void setCategorias(Set<CategoriaEntity> categorias) {
		this.categorias = categorias;
	}

	public Set<SubCategoriaEntity> getSubCategoria() {
		return subCategoria;
	}

	public void setSubCategoria(Set<SubCategoriaEntity> subCategoria) {
		this.subCategoria = subCategoria;
	}

	public Set<ImagemProdutoEntity> getImagemProduto() {
		return imagemProduto;
	}

	public void setImagemProduto(Set<ImagemProdutoEntity> imagemProduto) {
		this.imagemProduto = imagemProduto;
	}

	public Set<ProdutoVariacaoEntity> getProdutoVariacao() {
		return produtoVariacao;
	}

	public void setProdutoVariacao(Set<ProdutoVariacaoEntity> produtoVariacao) {
		this.produtoVariacao = produtoVariacao;
	}

	public Set<MarcaEntity> getMarcas() {
		return marcas;
	}

	public void setMarcas(Set<MarcaEntity> marcas) {
		this.marcas = marcas;
	}

	public Set<AvaliacaoEntity> getAvaliacoes() {
		return avaliacoes;
	}

	public void setAvaliacoes(Set<AvaliacaoEntity> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}
	public Long getIdProduto() {
		return idProduto;
	}
	public void addCategoria(CategoriaEntity categoria) {
		if (categorias == null) categorias = new HashSet<>();
		categorias.add(categoria);
	}
}
