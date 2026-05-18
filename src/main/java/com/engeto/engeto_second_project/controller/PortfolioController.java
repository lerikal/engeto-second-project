package com.engeto.engeto_second_project.controller;

import com.engeto.engeto_second_project.service.CryptoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/portfolio-value")
public class PortfolioController {

    private final CryptoService cryptoService;

    public PortfolioController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    // Přidejte endpoint pro výpočet celkové hodnoty portfolia (GET /portfolio-value),
    // který spočítá a vrátí celkovou hodnotu portfolia (součet price * quantity všech kryptoměn).

    @Operation(summary = "Výpočet celkové hodnoty portfolia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hodnota portfolia byla vypočítána")
    })
    @GetMapping
    public ResponseEntity<BigDecimal> getPortfolioValue() {
        return ResponseEntity.ok(cryptoService.getPortfolioValue());
    }
}
