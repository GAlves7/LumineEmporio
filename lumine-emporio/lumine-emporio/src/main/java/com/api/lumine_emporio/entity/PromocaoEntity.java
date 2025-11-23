package com.api.lumine_emporio.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_promocao")
public class PromocaoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_promocao")
	private Long idPromocao;
	
	@Column(unique = true, nullable = false)
	private double desconto;
	
	//Relacionamentos
	@OneToMany(mappedBy = "promocao", cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<ProdutoVariacaoEntity> produtosVariacao;


	public PromocaoEntity(double desconto, Set<ProdutoVariacaoEntity> produtosVariacao) {
		this.desconto = desconto;
		this.produtosVariacao = produtosVariacao;
	}
	
	public PromocaoEntity() {}



	//Getters e Setters
	public double getDesconto() {
		return desconto;
	}
	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}
	public Set<ProdutoVariacaoEntity> getProdutosVariacao() {
		return produtosVariacao;
	}
	public void setProdutosVariacao(Set<ProdutoVariacaoEntity> produtosVariacao) {
		this.produtosVariacao = produtosVariacao;
	}
}
