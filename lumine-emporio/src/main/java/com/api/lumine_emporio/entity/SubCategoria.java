package com.api.lumine_emporio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_subcategoria")
public class SubCategoria {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id_subcategoria")
	Long idSubCategoria;
	
	@Column(nullable=false, unique = true, length = 35)
	private String nome;
	
	@Column(nullable=false)
	private String descricao;
}
