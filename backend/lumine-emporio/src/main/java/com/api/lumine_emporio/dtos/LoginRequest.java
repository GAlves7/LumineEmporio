package com.api.lumine_emporio.dtos;

import jakarta.validation.constraints.NotNull;

public record LoginRequest (
		@NotNull String email,
		@NotNull String password
) {}

