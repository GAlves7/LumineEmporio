package com.api.lumine_emporio.dtos;

import java.util.UUID;

public class AddCarrinhoDTO {
	private UUID idUsuario;
	private Long idProdutoVariacao;
	private int quantidade;
	
	public AddCarrinhoDTO() {}
	
	public UUID getIdUsuario() {
		return idUsuario;
	}
	
	public void setIdUsuario(UUID idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public Long getIdProdutoVariacao() {
		return idProdutoVariacao;
	}
	
	public void setIdProdutoVariacao(Long idProdutoVariacao) {
		this.idProdutoVariacao = idProdutoVariacao;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
}