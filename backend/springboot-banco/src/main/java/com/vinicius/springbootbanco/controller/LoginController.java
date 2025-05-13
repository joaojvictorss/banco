package com.vinicius.springbootbanco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vinicius.springbootbanco.repository.UsuarioRepository;

@RestController
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
public ResponseEntity<?> login(@RequestParam String email, @RequestParam String senha) {
    var usuario = usuarioRepository.findByEmailAndSenha(email, senha);

    if (usuario != null) {
        return ResponseEntity.ok(usuario);
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos");
    }
}
}