package com.api.lumine_emporio.dtos;

import java.util.List;

import com.api.lumine_emporio.entity.enums.Tamanho;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProdutoVariacaoDTO(
		@DecimalMin(value = "0", inclusive = false, message = "Estoque nao pode ser 0.") int estoque,
		@NotNull Tamanho tamanho,
		@NotEmpty String descricao,
		double preco,
		List<Long> idCores
) {}