package com.api.lumine_emporio.dtos;

import jakarta.validation.constraints.NotNull;

public record VerificarCodigoDTO(
	String email,
	String codigo,
	String novaSenha
) {}