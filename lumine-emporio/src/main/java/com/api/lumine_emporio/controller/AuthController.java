package com.api.lumine_emporio.controller;

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
import com.api.lumine_emporio.dtos.LoginRequest;
import com.api.lumine_emporio.dtos.LoginResponse;
import com.api.lumine_emporio.dtos.RegisterRequest;
import com.api.lumine_emporio.dtos.RegisterResponse;
import com.api.lumine_emporio.entity.UsuarioEntity;
import com.api.lumine_emporio.entity.enums.Role;
import com.api.lumine_emporio.repository.UsuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private TokenConfig tokenConfig;
	
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
		
		usuarioRepository.save(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterResponse(usuario.getNome(), usuario.getEmail()));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
