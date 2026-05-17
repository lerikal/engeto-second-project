package com.engeto.engeto_second_project.controller;

import com.engeto.engeto_second_project.dto.CryptoRequestDTO;
import com.engeto.engeto_second_project.model.Crypto;
import com.engeto.engeto_second_project.model.SortType;
import com.engeto.engeto_second_project.service.CryptoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/cryptos")
public class CryptoController {

    private final CryptoService cryptoService;

    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    // Přidání nové kryptoměny do portfolia (POST /cryptos):
    // Přijímejte data kryptoměny v RequestBody a přidejte ji do seznamu.

    @PostMapping
    public ResponseEntity<Void> addCrypto(@RequestBody CryptoRequestDTO dto) {
        cryptoService.addCrypto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Získání všech kryptoměn (GET /cryptos):
    // Přidejte možnost řazení podle názvu, ceny nebo počtu jednotek pomocí parametru sort v Query podle následující pravidel:
    // pro řazení podle ceny : /cryptos?sort=price
    // pro řazení pode názvu : /cryptos?sort=name
    // pro řazení podle počtu jednotek : /cryptos?sort=quantity

    @GetMapping
    public ArrayList<Crypto> getPortfolio(@RequestParam(required = false) SortType sort) {
        return cryptoService.getPortfolio(sort);
    }

    // Získání detailu konkrétní kryptoměny (GET /cryptos/{id}):
    // Přijímejte id kryptoměny v cestě a vraťte detaily dané kryptoměny.

    @GetMapping("/{id}")
    public ResponseEntity<Crypto> getCrypto(@PathVariable int id) {
        return ResponseEntity.ok(cryptoService.getCryptoById(id));
    }

    // Aktualizace kryptoměny (PUT /cryptos/{id}),
    // kde přijmete aktualizovaná data kryptoměny přes RequestBody a identifikátor kryptoměny přes Path.
    @PutMapping("/{id}")
    public ResponseEntity<Crypto> updateCrypto(@RequestBody Crypto newCrypto, @PathVariable int id) {
        Crypto updatedCrypto = cryptoService.updateCrypto(newCrypto, id);
        return ResponseEntity.ok(updatedCrypto);
    }

    // navíc, aby bylo kompletní

    // Vymaz kryptoměny (DELETE /cryptos/{id}),
    // Přijímejte id kryptoměny v cestě a vymažte kryptoměnu z portfolia.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCrypto(@PathVariable int id) {
        boolean removed = cryptoService.removeCrypto(id);
        if (!removed) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
