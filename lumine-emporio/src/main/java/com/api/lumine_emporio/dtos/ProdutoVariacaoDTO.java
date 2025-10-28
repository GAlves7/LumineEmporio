package com.api.lumine_emporio.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;

public record ProdutoVariacaoDTO(
		@DecimalMin(value = "0", inclusive = false, message = "Estoque nao pode ser 0.") int estoque,
		@NotEmpty String tamanho,
		@NotEmpty String descricao,
		@DecimalMin(value = "0.0", inclusive = false, message = "Preco nao pode ser 0.") double preco	
) {}