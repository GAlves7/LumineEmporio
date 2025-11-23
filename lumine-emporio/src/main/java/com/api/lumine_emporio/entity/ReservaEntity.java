package com.api.lumine_emporio.entity;

import java.util.Set;

import com.api.lumine_emporio.entity.enums.FormaPagamento;
import com.api.lumine_emporio.entity.enums.StatusReserva;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


@Entity
public class ReservaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long idReserva;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private StatusReserva status;
	
	@Enumerated(EnumType.STRING)
	private FormaPagamento formaPagamento;
	
	@OneToMany
	private Set<ItemReservaEntity> itensReserva;
	
	@ManyToOne
	private UsuarioEntity usuarioEntity;

	public ReservaEntity(StatusReserva status, FormaPagamento formaPagamento, Set<ItemReservaEntity> itensReserva,
			UsuarioEntity usuarioEntity) {
		this.status = status;
		this.formaPagamento = formaPagamento;
		this.itensReserva = itensReserva;
		this.usuarioEntity = usuarioEntity;
	}

	public ReservaEntity() {}

	
	public StatusReserva getStatus() {
		return status;
	}

	public void setStatus(StatusReserva status) {
		this.status = status;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Set<ItemReservaEntity> getItensReserva() {
		return itensReserva;
	}

	public void setItensReserva(Set<ItemReservaEntity> itensReserva) {
		this.itensReserva = itensReserva;
	}

	public UsuarioEntity getUsuarioEntity() {
		return usuarioEntity;
	}

	public void setUsuarioEntity(UsuarioEntity usuarioEntity) {
		this.usuarioEntity = usuarioEntity;
	}

	public Long getIdReserva() {
		return idReserva;
	}
}
