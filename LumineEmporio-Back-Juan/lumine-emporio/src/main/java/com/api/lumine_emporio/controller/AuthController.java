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
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.api.lumine_emporio.dtos.VerificarCodigoDTO;
import com.api.lumine_emporio.entity.PasswordResetToken;
import com.api.lumine_emporio.entity.UsuarioEntity;
import com.api.lumine_emporio.entity.enums.Role;
import com.api.lumine_emporio.service.MailService;
import com.api.lumine_emporio.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
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
	
	
	@PostMapping("/redefinir-senha")
	public ResponseEntity<Object> recuperarSenha(@RequestBody @Valid RecuperarSenhaRequest recuperarSenhaRequest){
		UsuarioEntity usuario = usuarioService.findByEmail(recuperarSenhaRequest.email());
		usuarioService.criarTokenReset(usuario);
		
		return ResponseEntity.ok("Codigo de verificação enviado por email.");
	}
	
	
	@PostMapping("/verificar-codigo")
	public ResponseEntity<Object> verificarCodigo(@RequestBody VerificarCodigoDTO verificarCodigoDTO){
		UsuarioEntity usuario = usuarioService.findByEmail(verificarCodigoDTO.email());
		
		Optional<String> codigoOPT = usuarioService.getCodigoResetarSenha(usuario);
		
		if(codigoOPT.isPresent() && verificarCodigoDTO.codigo().equals(codigoOPT.get())) {
			usuario.setPassword(passwordEncoder.encode(verificarCodigoDTO.novaSenha()));
			usuarioService.save(usuario);
			usuarioService.deleteTokenByUsuario(usuario);
			return ResponseEntity.ok("senha alterada com sucesso.");
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Codigo invalido.");
	}
	
	
	@PostMapping("/verificar-link")
	public ResponseEntity<Object> verificarLink(@RequestParam String token, @RequestParam String senha){
		Optional<PasswordResetToken> passwordResetTokenOPT = usuarioService.findPasswordResetTokenByToken(token);
		if(passwordResetTokenOPT.isPresent()) {
			UsuarioEntity usuario = passwordResetTokenOPT.get().getUsuario();
			usuario.setPassword(passwordEncoder.encode(senha));
			usuarioService.save(usuario);
			usuarioService.deleteToken(passwordResetTokenOPT.get());
			return ResponseEntity.ok("senha alterada com sucesso.");
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Codigo invalido.");
	}
	
	
	
	
}
