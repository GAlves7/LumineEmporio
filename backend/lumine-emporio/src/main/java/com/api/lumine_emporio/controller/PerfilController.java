package com.api.lumine_emporio.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.api.lumine_emporio.config.TokenConfig;
import com.api.lumine_emporio.config.JWTUserData;
import com.api.lumine_emporio.dtos.AtualizarPerfilDTO;
import com.api.lumine_emporio.entity.UsuarioEntity;
import com.api.lumine_emporio.service.PerfilService;

@RestController
@RequestMapping("/api/perfil")
@CrossOrigin(origins = "*")
public class PerfilController {
	
	@Autowired
	private PerfilService perfilService;
	
	@Autowired
	private TokenConfig tokenConfig;
	
	// Método auxiliar para extrair email do token
	private String extrairEmailDoToken(String token) {
		if (token == null || token.isEmpty()) {
			throw new RuntimeException("Token não fornecido");
		}
		
		// Remove "Bearer " se existir
		if (token.startsWith("Bearer ")) {
			token = token.substring(7);
		}
		
		// Valida e extrai dados do token
		Optional<JWTUserData> dadosToken = tokenConfig.validateToken(token);
		if (!dadosToken.isPresent()) {
			throw new RuntimeException("Token inválido ou expirado");
		}
		
		// Retorna o email (subject do token)
		return dadosToken.get().email();
	}
	
	@GetMapping
	public ResponseEntity<?> obterPerfil(@RequestHeader("Authorization") String token) {
		try {
			String email = extrairEmailDoToken(token);
			UsuarioEntity usuario = perfilService.obterPerfil(email);
			
			Map<String, Object> resposta = new HashMap<>();
			resposta.put("nome", usuario.getNome());
			resposta.put("email", usuario.getEmail());
			resposta.put("telefone", usuario.getTelefone());
			resposta.put("cpf", usuario.getCpf());
			
			return ResponseEntity.ok(resposta);
		} catch (Exception e) {
			Map<String, String> erro = new HashMap<>();
			erro.put("erro", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
		}
	}
	
	@PutMapping
	public ResponseEntity<?> atualizarPerfil(
		@RequestHeader("Authorization") String token,
		@RequestBody AtualizarPerfilDTO dto
	) {
		try {
			String email = extrairEmailDoToken(token);
			UsuarioEntity usuarioAtualizado = perfilService.atualizarPerfil(email, dto);
			
			Map<String, Object> resposta = new HashMap<>();
			resposta.put("mensagem", "Perfil atualizado com sucesso!");
			resposta.put("nome", usuarioAtualizado.getNome());
			resposta.put("email", usuarioAtualizado.getEmail());
			resposta.put("telefone", usuarioAtualizado.getTelefone());
			
			return ResponseEntity.ok(resposta);
		} catch (Exception e) {
			Map<String, String> erro = new HashMap<>();
			erro.put("erro", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
		}
	}
}