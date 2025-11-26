package com.api.lumine_emporio.entity.enums;

public enum UsuarioStatus {
	A("ATIVO"),
	B("BLOQUEADO"),
	P("PENDENTE");
	
	private String descricao;

	private UsuarioStatus(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
