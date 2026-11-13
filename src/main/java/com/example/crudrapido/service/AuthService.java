package com.example.crudrapido.service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.example.crudrapido.dto.LoginDTO;
import com.example.crudrapido.dto.RegisterDTO;
import com.example.crudrapido.entity.Usuario;
import com.example.crudrapido.repository.UsuarioRepository;

@Service
public class AuthService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtEncoder jwtEncoder;

    //Devuelve false si el email ya existe
    public boolean registrar(RegisterDTO dto) {
        if (usuarioRepository.findByEmail(dto.email()).isPresent()) {
            return false;
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.nombre());
        usuario.setEmail(dto.email());
        usuario.setPassword(passwordEncoder.encode(dto.password()));
        usuarioRepository.save(usuario);
        return true;
    }

    //Devuelve el token si el email y password son correctos
    public Optional<String> login(LoginDTO dto) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(dto.email());
        if (usuario.isPresent() && passwordEncoder.matches(dto.password(), usuario.get().getPassword())) {
            return Optional.of(generarToken(usuario.get().getEmail()));
        }
        return Optional.empty();
    }

    //Crea un JWT que dura 1 hora
    private String generarToken(String email) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
            .subject(email)
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plusSeconds(3600))
            .build();
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }
}