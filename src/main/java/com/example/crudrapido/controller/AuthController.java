package com.example.crudrapido.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crudrapido.dto.LoginDTO;
import com.example.crudrapido.dto.RegisterDTO;
import com.example.crudrapido.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody RegisterDTO dto) {
        if (!authService.registrar(dto)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "El email ya existe"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensaje", "Usuario registrado"));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginDTO dto) {
        Optional<String> token = authService.login(dto);
        if (token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Email o password incorrectos"));
        }
        return ResponseEntity.ok(Map.of("token", token.get()));
    }
}