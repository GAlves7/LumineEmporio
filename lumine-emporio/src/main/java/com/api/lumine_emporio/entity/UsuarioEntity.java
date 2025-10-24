package com.api.lumine_emporio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.api.lumine_emporio.entity.enums.Role;


@Entity
@Table(name = "tb_usuario")
public class UsuarioEntity implements UserDetails{
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
	
	
	//Relacionamentos
	@OneToMany(mappedBy = "id_usuario")
	private AvaliacaoEntity avaliacaoEntity;

	@ManyToMany
	@JoinTable(
		name = "tb_favoritos",
		joinColumns = @JoinColumn(name = "id_usuario"),
		inverseJoinColumns = @JoinColumn(name = "id_produto")
	
	)
	private Set<ProdutoEntity> favoritos;
	
	@OneToMany(mappedBy = "id_usuario")
	private Set<ItemCarrinhoEntity> itensCarrinho;
	
	
	public UsuarioEntity(String nome, String senha, String email, String telefone, Role role,
			AvaliacaoEntity avaliacaoEntity, Set<ProdutoEntity> favoritos, Set<ItemCarrinhoEntity> itensCarrinho) {
		this.nome = nome;
		this.senha = senha;
		this.email = email;
		this.telefone = telefone;
		this.role = role;
		this.avaliacaoEntity = avaliacaoEntity;
		this.favoritos = favoritos;
		this.itensCarrinho = itensCarrinho;
	}

	
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
		// TODO Auto-generated method stub
		return email;
	}
	
	
	//Getters e Setters
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public AvaliacaoEntity getAvaliacaoEntity() {
		return avaliacaoEntity;
	}

	public void setAvaliacaoEntity(AvaliacaoEntity avaliacaoEntity) {
		this.avaliacaoEntity = avaliacaoEntity;
	}

	public Set<ProdutoEntity> getFavoritos() {
		return favoritos;
	}

	public void setFavoritos(Set<ProdutoEntity> favoritos) {
		this.favoritos = favoritos;
	}

	public Set<ItemCarrinhoEntity> getItensCarrinho() {
		return itensCarrinho;
	}

	public void setItensCarrinho(Set<ItemCarrinhoEntity> itensCarrinho) {
		this.itensCarrinho = itensCarrinho;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public UUID getIdUsuario() {
		return idUsuario;
	}
}
