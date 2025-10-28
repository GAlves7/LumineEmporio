package com.api.lumine_emporio.entity;

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
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_imagemprod")
	private Long idImagemProd;
	
	@Column(nullable=false, unique = true)
	private String url;
	
	
	//Relacionamentos
	@ManyToOne
	private ProdutoEntity produto;
	
	
	public ImagemProdutoEntity(ProdutoEntity produto, Long idImagemProd) {
		this.produto = produto;
		this.idImagemProd = idImagemProd;
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
