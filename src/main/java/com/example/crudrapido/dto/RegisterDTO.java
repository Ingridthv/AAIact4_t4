package com.example.crudrapido.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(
    @NotBlank String nombre,
    @NotBlank @Email String email,
    @NotBlank String password
) {
    
}