package com.api.lumine_emporio.dtos;

import jakarta.validation.constraints.NotEmpty;


public record CorDTO (
		@NotEmpty String nome,
		@NotEmpty String codigoHex
) {}