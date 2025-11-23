package com.api.lumine_emporio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_avaliacao")
public class AvaliacaoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long idAvaliacao;
	
	@Column(nullable=false, length = 60)
	private String comentario;
	
	@Column(nullable = false)
	private Long nota;
	
	
	//Relacionamentos
	@ManyToOne
	private UsuarioEntity usuario;
	
	@ManyToOne
	private ProdutoEntity produto;

	public AvaliacaoEntity(String comentario, Long nota, UsuarioEntity usuario, ProdutoEntity produto) {
		this.comentario = comentario;
		this.nota = nota;
		this.usuario = usuario;
		this.produto = produto;
	}
	
	
	//Getters e Setters
	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Long getNota() {
		return nota;
	}

	public void setNota(Long nota) {
		this.nota = nota;
	}

	public UsuarioEntity getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEntity usuario) {
		this.usuario = usuario;
	}

	public ProdutoEntity getProduto() {
		return produto;
	}

	public void setProduto(ProdutoEntity produto) {
		this.produto = produto;
	}

	public Long getIdAvaliacao() {
		return idAvaliacao;
	}
}
