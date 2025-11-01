package com.api.lumine_emporio.dtos;

import jakarta.validation.constraints.NotNull;

public record RegisterRequest (
		@NotNull String nome,
		@NotNull String email,
		@NotNull String password,
		@NotNull String telefone
) {}
