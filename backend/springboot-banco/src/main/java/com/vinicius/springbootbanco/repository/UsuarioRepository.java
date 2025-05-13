package com.vinicius.springbootbanco.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinicius.springbootbanco.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmailAndSenha(String email, String senha);
}
