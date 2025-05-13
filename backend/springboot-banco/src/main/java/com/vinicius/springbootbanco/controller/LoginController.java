package com.vinicius.springbootbanco.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vinicius.springbootbanco.model.Conta;
import com.vinicius.springbootbanco.repository.ContaRepository;
import com.vinicius.springbootbanco.util.PasswordUtil;

@RestController
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    private ContaRepository contaRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String senha) {
        // Converte a senha para hash SHA-256
        String hashedSenha = PasswordUtil.hashPassword(senha);

        Optional<Conta> contaOpt = contaRepository.findByUsuarioEmailAndSenha(email, hashedSenha);

        if (contaOpt.isPresent()) {
            return ResponseEntity.ok(contaOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos");
        }
    }
}