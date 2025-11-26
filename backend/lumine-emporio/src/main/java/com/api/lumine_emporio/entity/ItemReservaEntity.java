package com.api.lumine_emporio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemReservaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long idItem;
	
	@Column(nullable = false)
	private int quantidade;
	
	@ManyToOne
	@JoinColumn(name = "id_produtovariacao", nullable = false)
	private ProdutoVariacaoEntity produtoVariacaoEntity;
	
	@ManyToOne
	@JoinColumn(name = "id_reserva", nullable = false)
	private ReservaEntity reservaEntity;

	public ItemReservaEntity(int quantidade, ProdutoVariacaoEntity produtoVariacaoEntity, ReservaEntity reservaEntity) {
		this.quantidade = quantidade;
		this.produtoVariacaoEntity = produtoVariacaoEntity;
		this.reservaEntity = reservaEntity;
	}

	public ItemReservaEntity() {}

	
	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public ProdutoVariacaoEntity getProdutoVariacaoEntity() {
		return produtoVariacaoEntity;
	}

	public void setProdutoVariacaoEntity(ProdutoVariacaoEntity produtoVariacaoEntity) {
		this.produtoVariacaoEntity = produtoVariacaoEntity;
	}

	public ReservaEntity getReservaEntity() {
		return reservaEntity;
	}

	public void setReservaEntity(ReservaEntity reservaEntity) {
		this.reservaEntity = reservaEntity;
	}

	public Long getIdItem() {
		return idItem;
	}
}
