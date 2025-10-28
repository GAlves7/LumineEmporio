package com.api.lumine_emporio.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;

public record ProdutoDTO(
		 @NotEmpty String nome, 
		 @NotEmpty String descricao, 
		 @DecimalMin(value = "0.0", inclusive = false, message = "Preco nao pode ser 0.") double preco
) {}