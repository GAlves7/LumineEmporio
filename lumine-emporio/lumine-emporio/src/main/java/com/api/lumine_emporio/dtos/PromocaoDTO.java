package com.api.lumine_emporio.dtos;

import java.util.List;



import jakarta.validation.constraints.DecimalMin;

public record PromocaoDTO (
	@DecimalMin(value = "0.0", inclusive = false, message = "Desconto nao pode ser 0.") double desconto,
	List<Long> produtosVariacao
) {}
