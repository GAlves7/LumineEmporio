package com.api.lumine_emporio.entity.enums;


/*@JsonFormat(shape = JsonFormat.Shape.OBJECT)*/
 public enum Role {
	A("Administrador"),
	B("Basico");
	
	private String descricao;
	
	private Role(String descricao) {
		this.descricao = descricao;
	}
	
	/*@JsonCreator*/
	public static Role getRole(String codigo) {
		if (codigo.equals("A")) return A;
		else if (codigo.equals("B")) return B;
		return null;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
