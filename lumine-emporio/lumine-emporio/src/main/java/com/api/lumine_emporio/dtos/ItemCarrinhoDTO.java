package com.api.lumine_emporio.dtos;

import java.util.List;

import com.api.lumine_emporio.entity.enums.Tamanho;

public record ItemCarrinhoDTO(
		String nomeProdutoVar,
		Long idProdutoVar,
		Long idProduto,
		Tamanho tamanho,
		double preco,
		int quantidade
) {}