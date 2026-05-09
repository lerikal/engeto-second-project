package com.engeto.engeto_second_project;

import java.util.ArrayList;
import java.util.Comparator;

public class Portfolio extends ArrayList<Crypto> {

    /**
     *  Přidání nové kryptoměny do portfolia
     * @param crypto kryptoměna
     */
    public void addCrypto(Crypto crypto) {
        this.add(crypto);
    }

    /**
     *  Výpis všech kryptoměn v portfoliu.
     */
    public void printPortfolio() {
        this.stream().forEach(crypto -> System.out.println(crypto.toString()));
    }

    /**
     *  Metoda pro řazení podle názvu kryptoměny.
     */
    public void sortByName() {
        this.sort(Comparator.comparing(Crypto::getName));
    }

    /**
     *  Metoda pro řazení podle ceny kryptoměny.
     */
    public void sortByPrice() {
        this.sort(Comparator.comparing(Crypto::getPrice));
    }

    /**
     *  Metoda pro řazení podle počtu jednotek kryptoměny v portfoliu.
     */
    public void sortByAmount() {
        this.sort(Comparator.comparing(Crypto::getQuantity));
    }

}
