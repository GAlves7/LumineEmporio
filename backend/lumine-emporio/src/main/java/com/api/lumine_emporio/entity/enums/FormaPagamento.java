package com.api.lumine_emporio.entity.enums;

public enum FormaPagamento {
	V("A vista"),
	C("Cartao"),
	P("Pix");
	
	private String descricao;

	FormaPagamento(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescrcao() {
		return descricao;
	}
}
