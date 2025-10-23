package com.api.lumine_emporio.entity.enums;

public enum Tamanho {
	PP("Extra Pequeno"),
	P("Pequeno"),
	M("Médio"),
	G("Grande"),
	GG("Extra Grande");
	
	private String descricao;

	private Tamanho(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
