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
	
	public static Tamanho getTamanho(String cod) {
		if(cod.equalsIgnoreCase("pp")) return PP;
		if(cod.equalsIgnoreCase("P")) return P;
		if(cod.equalsIgnoreCase("M")) return M;
		if(cod.equalsIgnoreCase("G")) return G;
		if(cod.equalsIgnoreCase("GG")) return GG;
		return null;
	}
}
