package com.engeto.engeto_second_project;

public class Crypto {
    private int id;             // unikátní identifikátor kryptoměny
    private String name;        // název kryptoměny (např. Bitcoin, Ethereum)
    private String symbol;      // zkratka kryptoměny (např. BTC, ETH)
    private double price;       // aktuální cena kryptoměny v dolarech
    private double quantity;    // počet jednotek kryptoměny v portfoliu

    public Crypto(int id, String name, String symbol, double price, double quantity) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return this.getId() + " " + this.getName() + " " + this.getSymbol() + " " + this.getPrice() + " " + this.getQuantity();
    }
}
