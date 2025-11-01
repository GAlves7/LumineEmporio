package com.api.lumine_emporio.config;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.api.lumine_emporio.entity.UsuarioEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class TokenConfig {
	private String secret = "pensadir";
	
	public String generateToken(UsuarioEntity usuario) {
		Algorithm algorithm = Algorithm.HMAC256(secret);
		
		return JWT.create()
				.withClaim("UserId", usuario.getIdUsuario().toString())
				.withSubject(usuario.getEmail())
				.withExpiresAt(Instant.now().plusSeconds(86400))
				.withIssuedAt(Instant.now())
				.sign(algorithm);
	}
	
	
	public Optional<JWTUserData> validateToken(String token){
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			DecodedJWT decode = JWT.require(algorithm).build().verify(token);
			
			return Optional.of(new JWTUserData(decode.getClaim("userId").asLong(), decode.getSubject()));
			
		}catch (JWTVerificationException ex) {
			return Optional.empty();
		}
	}
}
