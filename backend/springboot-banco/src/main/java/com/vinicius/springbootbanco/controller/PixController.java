package com.vinicius.springbootbanco.controller;

import com.vinicius.springbootbanco.model.Conta;
import com.vinicius.springbootbanco.model.Transacao;
import com.vinicius.springbootbanco.repository.ContaRepository;
import com.vinicius.springbootbanco.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pix")
@CrossOrigin(origins = "*")
public class PixController {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    // Extrato Pix
    @GetMapping("/extrato/{contaId}")
    public ResponseEntity<List<Transacao>> getExtrato(@PathVariable Long contaId) {
        List<Transacao> transacoes = transacaoRepository.findByContaOrigemOrContaDestino(contaId, contaId);
        return ResponseEntity.ok(transacoes);
    }

    // Transferência Pix
    @PostMapping("/transferir")
    public ResponseEntity<String> transferirPix(
            @RequestParam Long contaOrigem,
            @RequestParam Long contaDestino,
            @RequestParam Double valor) {

        Conta origem = contaRepository.findById(contaOrigem).orElse(null);
        Conta destino = contaRepository.findById(contaDestino).orElse(null);

        if (origem == null || destino == null) {
            return ResponseEntity.badRequest().body("Conta não encontrada.");
        }
        if (origem.getSaldo() < valor) {
            return ResponseEntity.badRequest().body("Saldo insuficiente.");
        }

        origem.setSaldo(origem.getSaldo() - valor);
        destino.setSaldo(destino.getSaldo() + valor);
        contaRepository.save(origem);
        contaRepository.save(destino);

        Transacao transacao = new Transacao();
        transacao.setContaOrigem(contaOrigem);
        transacao.setContaDestino(contaDestino);
        transacao.setValor(valor);
        transacao.setTipo("pix");
        transacaoRepository.save(transacao);

        return ResponseEntity.ok("Transferência Pix realizada com sucesso.");
    }
}