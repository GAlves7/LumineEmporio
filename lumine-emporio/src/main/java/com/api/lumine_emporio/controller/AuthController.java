package com.api.lumine_emporio.controller;

import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.lumine_emporio.config.TokenConfig;
import com.api.lumine_emporio.dtos.CodigoAndEmail;
import com.api.lumine_emporio.dtos.LoginRequest;
import com.api.lumine_emporio.dtos.LoginResponse;
import com.api.lumine_emporio.dtos.RecuperarSenhaRequest;
import com.api.lumine_emporio.dtos.RegisterRequest;
import com.api.lumine_emporio.dtos.RegisterResponse;
import com.api.lumine_emporio.entity.UsuarioEntity;
import com.api.lumine_emporio.entity.enums.Role;
import com.api.lumine_emporio.entity.enums.UsuarioStatus;
import com.api.lumine_emporio.service.CodigoVerificacaoService;
import com.api.lumine_emporio.service.LinkVerificacaoService;
import com.api.lumine_emporio.service.UsuarioService;

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
	private CodigoVerificacaoService codigoVerificacaoService;
	@Autowired
	private LinkVerificacaoService linkVerificacaoService;
	
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest login){
		
		UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(login.email(), login.password());
		Authentication authentication = authenticationManager.authenticate(userAndPass);
		
		UsuarioEntity usuario = (UsuarioEntity) authentication.getPrincipal();
		if(!usuario.getStatus().equals(UsuarioStatus.A)) 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		
		String token = tokenConfig.generateToken(usuario);
		return ResponseEntity.ok(new LoginResponse(token));
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest){
		UsuarioEntity usuario = new UsuarioEntity();
		usuario.setNome(registerRequest.nome());
		usuario.setEmail(registerRequest.email());
		usuario.setTelefone(registerRequest.telefone());
		usuario.setCpf(registerRequest.cpf());
		usuario.setRole(Role.B);
		usuario.setStatus(UsuarioStatus.P);
		usuario.setPassword(passwordEncoder.encode(registerRequest.password()));
		
		usuarioService.save(usuario);
		codigoVerificacaoService.criarCodigo(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterResponse(usuario.getNome(), usuario.getEmail()));
	}
	
	
	@PostMapping("/register/verificar-email")
	public ResponseEntity<String> registerVerificarEmail(@Valid @RequestBody CodigoAndEmail codigoAndEmail){
		UsuarioEntity usuario = usuarioService.findByEmail(codigoAndEmail.email());
		if (codigoVerificacaoService.compararCodigo(usuario, codigoAndEmail.codigo())) {
			usuario.setStatus(UsuarioStatus.A);
			codigoVerificacaoService.deletebyUsuario(usuario);
			return ResponseEntity.ok("Email autenticado com sucesso.");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email ou codigo invalido.");
		
	}
	
	
	@PostMapping("/redefinir-senha")
	public ResponseEntity<Object> recuperarSenha(@RequestBody @Valid RecuperarSenhaRequest recuperarSenhaRequest){
		UsuarioEntity usuario = usuarioService.findByEmail(recuperarSenhaRequest.email());
		linkVerificacaoService.criarLink(usuario);
		
		return ResponseEntity.ok("Codigo de verificação enviado por email.");
	}
	
	
	@PostMapping("/verificar-link")
	public ResponseEntity<Object> verificarLink(@RequestParam String token, @RequestParam String novaSenha){
		Optional<UsuarioEntity> usuarioOpt = linkVerificacaoService.consultarLink(token);
		
		if(usuarioOpt.isPresent()) {
			UsuarioEntity usuario = usuarioOpt.get();
			usuario.setPassword(passwordEncoder.encode(novaSenha));
			linkVerificacaoService.deleteByUsuario(usuario);
			usuarioService.save(usuario);
			
			return ResponseEntity.ok("senha alterada com sucesso.");
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Link invalido.");
	}
	
	
	
	
}
