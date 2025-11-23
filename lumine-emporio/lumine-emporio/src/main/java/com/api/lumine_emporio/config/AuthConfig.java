package com.api.lumine_emporio.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.api.lumine_emporio.repository.UsuarioRepository;

@Service
public class AuthConfig implements UserDetailsService{
	@Autowired
	private UsuarioRepository usuariRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return usuariRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
	}
}
