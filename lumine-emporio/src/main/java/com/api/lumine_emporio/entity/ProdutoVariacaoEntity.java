package com.api.lumine_emporio.entity;

import java.util.Set;

import com.api.lumine_emporio.entity.enums.Tamanho;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_produtovariacao")
public class ProdutoVariacaoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id_produtovariacao")
	private Long idProdutoVar;
	
	@Column(nullable=false)
	private int estoque;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private Tamanho tamanho;
	
	@Column(nullable=false)
	private String descricao;
	
	@Column(nullable = false)
	private double preco;
	
	
	//Relacionamentos
	@ManyToOne
	@JsonBackReference
	private ProdutoEntity produto;
	
	@ManyToMany
	@JoinTable(
			name = "produtoVariacao_cor",
			joinColumns = @JoinColumn(name = "id_produtovariacao"),
			inverseJoinColumns = @JoinColumn(name = "id_cor")
	)
	private Set<CorEntity> cores;
	
	@ManyToOne
	@JoinColumn(name = "promocao")
	@JsonBackReference
	private PromocaoEntity promocao;
	
	
	public ProdutoVariacaoEntity(int estoque, Tamanho tamanho, String descricao, double preco, ProdutoEntity produto,
			Set<CorEntity> cores) {
		this.estoque = estoque;
		this.tamanho = tamanho;
		this.descricao = descricao;
		this.preco = preco;
		this.produto = produto;
		this.cores = cores;
	}
	

	public ProdutoVariacaoEntity() {}
	

	//Getters e Setters
	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}

	public Tamanho getTamanho() {
		return tamanho;
	}

	public void setTamanho(Tamanho tamanho) {
		this.tamanho = tamanho;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public ProdutoEntity getProduto() {
		return produto;
	}

	public void setProduto(ProdutoEntity produto) {
		this.produto = produto;
	}

	public Set<CorEntity> getCores() {
		return cores;
	}

	public void setCores(Set<CorEntity> cores) {
		this.cores = cores;
	}

	public Long getIdProdutoVar() {
		return idProdutoVar;
	}

	public PromocaoEntity getPromocao() {
		return promocao;
	}

	public void setPromocao(PromocaoEntity promocao) {
		this.promocao = promocao;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}
}
