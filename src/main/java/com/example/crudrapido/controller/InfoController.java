package com.example.crudrapido.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    //Endpoint publico: se puede abrir en el navegador sin token
    @GetMapping("/api/info")
    public Map<String, Object> info() {
        return Map.of(
            "proyecto", "AAIact4_t4",
            "autora", "Ingrid Arcadio Aparicio",
            "descripcion", "API REST de estudiantes con Spring Security y JWT",
            "login", "/api/auth/login",
            "estudiantes", "/api/v1/students (requiere token)"
        );
    }
}