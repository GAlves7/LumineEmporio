package com.api.lumine_emporio.entity.enums;

public enum FormaPagamento {
	V("A vista"),
	C("Cartao"),
	B("Boleto");
	
	private String descricao;

	FormaPagamento(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescrcao() {
		return descricao;
	}
}
