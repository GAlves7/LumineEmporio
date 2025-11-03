package com.api.lumine_emporio.entity.enums;


/*@JsonFormat(shape = JsonFormat.Shape.OBJECT)*/
 public enum Role {
	ADMIN("A", "Administrador"),
	BASIC("B", "Basico");
	
	private String codigo;
	private String descricao;
	
	private Role(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	/*@JsonCreator*/
	public static Role getRole(String codigo) {
		if (codigo.equals("A")) return ADMIN;
		else if (codigo.equals("B")) return BASIC;
		return null;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
}
