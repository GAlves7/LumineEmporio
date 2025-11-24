package com.api.lumine_emporio.config;

import java.util.UUID;

public record JWTUserData(
		UUID userId,
		String email
) {}
