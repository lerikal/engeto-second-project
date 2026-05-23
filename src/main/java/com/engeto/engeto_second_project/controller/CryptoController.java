package com.engeto.engeto_second_project.controller;

import com.engeto.engeto_second_project.dto.CryptoRequestDTO;
import com.engeto.engeto_second_project.dto.CryptoResponseDTO;
import com.engeto.engeto_second_project.dto.CryptoUpdateDTO;
import com.engeto.engeto_second_project.service.CryptoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/cryptos")
public class CryptoController {

    private final CryptoService cryptoService;

    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    // Přidání nové kryptoměny do portfolia (POST /cryptos):
    // Přijímejte data kryptoměny v RequestBody a přidejte ji do seznamu.

    @Operation(summary = "Přidání nové kryptoměny do portfolia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Kryptoměna byla vytvořena"),
            @ApiResponse(responseCode = "400", description = "Neplatná vstupní data")
    })
    @PostMapping
    public CryptoResponseDTO addCrypto(@RequestBody CryptoRequestDTO dto) {
        return cryptoService.addCrypto(dto);
    }

    // Získání všech kryptoměn (GET /cryptos):
    // Přidejte možnost řazení podle názvu, ceny nebo počtu jednotek pomocí parametru sort v Query podle následující pravidel:
    // pro řazení podle ceny : /cryptos?sort=price
    // pro řazení pode názvu : /cryptos?sort=name
    // pro řazení podle počtu jednotek : /cryptos?sort=quantity

    @Operation(summary = "Získání všech kryptoměn")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seznam kryptoměn byl vrácen"),
    })
    @GetMapping
    public ArrayList<CryptoResponseDTO> getPortfolio(@RequestParam(required = false) String sort) {
        return cryptoService.getPortfolio(sort);
    }

    // Získání detailu konkrétní kryptoměny (GET /cryptos/{id}):
    // Přijímejte id kryptoměny v cestě a vraťte detaily dané kryptoměny.

    @Operation(summary = "Získání detailu konkrétní kryptoměny")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Kryptoměna nalezena"),
            @ApiResponse(responseCode = "404", description = "Kryptoměna nebyla nalezena")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CryptoResponseDTO> getCrypto(@PathVariable UUID id) {
        return ResponseEntity.ok(cryptoService.getCryptoByIdDTO(id));
    }

    // Aktualizace kryptoměny (PUT /cryptos/{id}),
    // kde přijmete aktualizovaná data kryptoměny přes RequestBody a identifikátor kryptoměny přes Path.

    @Operation(summary = "Aktualizace kryptoměny")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Kryptoměna byla aktualizována"),
            @ApiResponse(responseCode = "404", description = "Kryptoměna nebyla nalezena")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CryptoResponseDTO> updateCrypto(@RequestBody CryptoUpdateDTO dto, @PathVariable UUID id) {
        return ResponseEntity.ok(cryptoService.updateCrypto(dto, id));
    }

    // navíc, aby bylo kompletní

    // Smazání kryptoměny (DELETE /cryptos/{id}),
    // Přijímejte id kryptoměny v cestě a vymažte kryptoměnu z portfolia.
    @Operation(summary = "Výmaz kryptoměny")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Kryptoměna byla smazána"),
            @ApiResponse(responseCode = "404", description = "Kryptoměna nebyla nalezena")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCrypto(@PathVariable UUID id) {
        boolean removed = cryptoService.removeCrypto(id);
        if (!removed) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
