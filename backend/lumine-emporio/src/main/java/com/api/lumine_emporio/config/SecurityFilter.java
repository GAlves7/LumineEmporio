package com.api.lumine_emporio.config;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.api.lumine_emporio.entity.UsuarioEntity;
import com.api.lumine_emporio.service.UsuarioService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenConfig tokenConfig;

    @Autowired
    private UsuarioService usuarioService ;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizedHeader = request.getHeader("Authorization");

        if (Strings.isNotEmpty(authorizedHeader) && authorizedHeader.startsWith("Bearer ")) {

            String token = authorizedHeader.substring(7);
            Optional<JWTUserData> optUser = tokenConfig.validateToken(token);

            if (optUser.isPresent() && SecurityContextHolder.getContext().getAuthentication() == null) {

                JWTUserData userData = optUser.get();

                // 1️⃣ BUSCA O USUARIO REAL NO BANCO
                UsuarioEntity usuario = usuarioService.findById(userData.userId()).orElse(null);

                if (usuario != null) {

                    // 2️⃣ COLOCA O USUARIO NO CONTEXTO
                    UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                usuario,
                                null,
                                List.of() // se você não tem roles
                        );

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
