package com.api.lumine_emporio.dtos;

import jakarta.validation.constraints.NotNull;

public record MarcaDTO(
		@NotNull String nome,
		@NotNull String descricao
) {}
