package com.api.lumine_emporio.dtos;


import jakarta.validation.constraints.NotNull;

public record CategoriaDTO (
		@NotNull String nome,
		@NotNull String descricao
) {}