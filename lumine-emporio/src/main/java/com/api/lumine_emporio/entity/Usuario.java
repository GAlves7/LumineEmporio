package com.api.lumine_emporio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.api.lumine_emporio.entity.enums.Role;


@Entity
@Table(name = "tb_usuario")
public class Usuario implements UserDetails{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_usuario")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID idUsuario;
	
	@Column(nullable=false, length = 35)
	private String nome;
	
	@Column(nullable=false)
	private String senha;
	
	@Column(nullable=false, unique = true, length = 35)
	private String email;
	
	@Column(nullable=false, unique = true, length = 35)
	private String telefone;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private Role role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(this.role == Role.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	public Usuario(String nome, String senha, String email, String telefone, Role role) {
		this.nome = nome;
		this.senha = senha;
		this.email = email;
		this.telefone = telefone;
		this.role = role;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public UUID getIdUsuario() {
		return idUsuario;
	}

	public Role getRole() {
		return role;
	}
}
