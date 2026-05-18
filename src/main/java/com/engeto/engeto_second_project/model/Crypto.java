package com.engeto.engeto_second_project.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Crypto {
    private UUID id;            // unikátní identifikátor kryptoměny  (ID bylo vylepšeno z int do UUID)
    private String name;        // název kryptoměny (např. Bitcoin, Ethereum)
    private String symbol;      // zkratka kryptoměny (např. BTC, ETH)
    private BigDecimal price;   // aktuální cena kryptoměny v dolarech
    private double quantity;    // počet jednotek kryptoměny v portfoliu

    public Crypto(UUID id, String name, String symbol, BigDecimal price, double quantity) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
    }
    public Crypto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

}
