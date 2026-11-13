package com.example.crudrapido.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crudrapido.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}