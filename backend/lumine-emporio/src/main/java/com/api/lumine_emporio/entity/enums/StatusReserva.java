package com.api.lumine_emporio.entity.enums;

public enum StatusReserva {
	A("Aprovada"),
	P("Pendente"),
	F("Finalizada");
	
	private String descricao;
	
	StatusReserva(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	
}
