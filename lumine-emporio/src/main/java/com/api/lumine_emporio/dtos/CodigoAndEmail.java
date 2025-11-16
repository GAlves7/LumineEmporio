package com.api.lumine_emporio.dtos;

import jakarta.validation.constraints.NotNull;

public record CodigoAndEmail(
		@NotNull String email,
		@NotNull String codigo
) {}
