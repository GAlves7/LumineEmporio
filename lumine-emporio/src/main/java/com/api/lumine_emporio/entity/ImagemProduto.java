package com.api.lumine_emporio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_imagemproduto")
public class ImagemProduto {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_imagemprod")
	Long idImagemProd;
	
	@Column(nullable=false, unique = true)
	String url;
}
