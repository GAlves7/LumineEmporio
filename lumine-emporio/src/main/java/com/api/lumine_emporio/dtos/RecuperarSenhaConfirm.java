package com.api.lumine_emporio.dtos;

import jakarta.validation.constraints.NotNull;

public record RecuperarSenhaConfirm (
		@NotNull Integer codVerificacao,
		@NotNull String token,
		@NotNull String newPassword
) {}
