package com.api.lumine_emporio.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_imagemproduto")
public class ImagemProdutoEntity {
	@Id
	@Column(name = "id_imagemprod")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idImagemProd;
	
	@Column(nullable=false, unique = true)
	private String url;
	
	
	//Relacionamentos
	@ManyToOne
	@JsonBackReference
	private ProdutoEntity produto;
	
	
	public ImagemProdutoEntity(String url, ProdutoEntity produto) {
		this.url = url;
		this.produto = produto;
	}


	public ImagemProdutoEntity() {}


	//Getters e Setters
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ProdutoEntity getProduto() {
		return produto;
	}

	public void setProduto(ProdutoEntity produto) {
		this.produto = produto;
	}
	
	public Long getIdImagemProd() {
		return idImagemProd;
	}

	public void setIdImagemProd(Long idImagemProd) {
		this.idImagemProd = idImagemProd;
	}
}
