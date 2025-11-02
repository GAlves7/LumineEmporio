package com.api.lumine_emporio.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.lumine_emporio.config.TokenConfig;
import com.api.lumine_emporio.dtos.CodigoAndEmail;
import com.api.lumine_emporio.dtos.LoginRequest;
import com.api.lumine_emporio.dtos.LoginResponse;
import com.api.lumine_emporio.dtos.RecuperarSenhaConfirm;
import com.api.lumine_emporio.dtos.RecuperarSenhaRequest;
import com.api.lumine_emporio.dtos.RegisterRequest;
import com.api.lumine_emporio.dtos.RegisterResponse;
import com.api.lumine_emporio.entity.UsuarioEntity;
import com.api.lumine_emporio.entity.enums.Role;
import com.api.lumine_emporio.service.MailService;
import com.api.lumine_emporio.service.UsuarioService;

import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private TokenConfig tokenConfig;
	@Autowired
	private MailService mailService;
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest login){
		
		UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(login.email(), login.password());
		Authentication authentication = authenticationManager.authenticate(userAndPass);
		
		UsuarioEntity usuario = (UsuarioEntity) authentication.getPrincipal();
		
		String token = tokenConfig.generateToken(usuario);
		return ResponseEntity.ok(new LoginResponse(token));
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest){
		UsuarioEntity usuario = new UsuarioEntity();
		usuario.setNome(registerRequest.nome());
		usuario.setEmail(registerRequest.email());
		usuario.setTelefone(registerRequest.telefone());
		usuario.setRole(Role.BASIC);
		usuario.setPassword(passwordEncoder.encode(registerRequest.password()));
		
		usuarioService.save(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterResponse(usuario.getNome(), usuario.getEmail()));
	}
	
	
	@PostMapping("/recuperar-senha")
	public ResponseEntity<Object> recuperarSenha(@RequestBody @Valid RecuperarSenhaRequest recuperarSenhaRequest){
		int codVerificacao = new Random().nextInt(100000);
		UsuarioEntity usuario = usuarioService.findByEmail(recuperarSenhaRequest.email());
		Map<String, String> response = new HashMap<>();
		response.put("token", tokenConfig.generateRecuperarSenhaToken(usuario, codVerificacao));
		
		mailService.enviarEmailTexto(usuario.getEmail(), "Código de Verificação.", "Código de Verificação:"+codVerificacao);
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/recuperar-senha/confirm")
	public ResponseEntity<Object> recuperarSenhaConfirm(@RequestBody @Valid RecuperarSenhaConfirm recuperarSenhaConfirm){
		Optional<CodigoAndEmail> optCodigoAndEmailt = tokenConfig.recuperarCod(recuperarSenhaConfirm.token());
		
		if(optCodigoAndEmailt.isEmpty()) 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token invalido ou expirado.");
		CodigoAndEmail codigoAndEmail = optCodigoAndEmailt.get();
		
		if(codigoAndEmail.codigo() == recuperarSenhaConfirm.codVerificacao()) {
			UsuarioEntity usuario = usuarioService.findByEmail(codigoAndEmail.email());
			usuario.setPassword(passwordEncoder.encode(recuperarSenhaConfirm.newPassword()));
			usuarioService.save(usuario);
			return ResponseEntity.ok(new RegisterResponse(usuario.getNome(), usuario.getEmail()));
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Codigo invalido.");
		
			
	}
	
	
	
	
	
	
	
}
