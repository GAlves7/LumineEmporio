package com.api.lumine_emporio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_itemcarrinho")
public class ItemCarrinhoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_itemcarrinho")
    private Long idItemCarrinho;

    @Column(nullable = false)
    private int quantidade;

    // Relacionamentos obrigatórios
    @ManyToOne(optional = false) // não pode ser nulo
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne(optional = false) // não pode ser nulo
    @JoinColumn(name = "id_produtovariacao", nullable = false)
    private ProdutoVariacaoEntity produtoVariacao;

    public ItemCarrinhoEntity(int quantidade, UsuarioEntity usuario, ProdutoVariacaoEntity produtoVariacao) {
        this.quantidade = quantidade;
        this.usuario = usuario;
        this.produtoVariacao = produtoVariacao;
    }

    public ItemCarrinhoEntity() {}

    // Getters e Setters
    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public ProdutoVariacaoEntity getProdutoVariacao() {
        return produtoVariacao;
    }

    public void setProdutoVariacao(ProdutoVariacaoEntity produtoVariacao) {
        this.produtoVariacao = produtoVariacao;
    }

    public Long getIdItemCarrinho() {
        return idItemCarrinho;
    }
}
