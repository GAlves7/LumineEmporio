package com.api.lumine_emporio.dtos;

import jakarta.validation.constraints.NotNull;

public record RecuperarSenhaRequest(
		@NotNull String email
) {}
