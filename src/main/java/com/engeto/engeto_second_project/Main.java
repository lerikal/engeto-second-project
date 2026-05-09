package com.engeto.engeto_second_project;

import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        Portfolio cryptos = new Portfolio();

        cryptos.addCrypto(new Crypto(1, "Bitcoin", "BTC", 80342.97, 7));
        cryptos.addCrypto(new Crypto(2, "Ethereum", "ETH", 47736.63, 2));
        cryptos.addCrypto(new Crypto(3, "Solana", "SOL", 93.23, 5));


        // Řazení podle názvu
        System.out.println("Podle názvu kryptoměny:");
        cryptos.sortByName();
        cryptos.printPortfolio();

        // Řazení podle ceny kryptoměny v dolarech
        System.out.println("\nPodle ceny kryptoměny v dolarech:");
        cryptos.sortByPrice();
        cryptos.printPortfolio();

        // Řazení podle počtu jednotek kryptoměny v portfoliu
        System.out.println("\nPodle počtu jednotek kryptoměny v portfoliu:");
        cryptos.sortByAmount();
        cryptos.printPortfolio();
    }
}
