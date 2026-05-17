package com.engeto.engeto_second_project.service;

import com.engeto.engeto_second_project.dto.CryptoRequestDTO;
import com.engeto.engeto_second_project.model.Crypto;
import com.engeto.engeto_second_project.model.SortType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;

@Service
public class CryptoService {

    // Vytvořte ArrayList typuCrypto, který bude uchovávat seznam kryptoměn v portfoliu.
    private final ArrayList<Crypto> portfolio = new ArrayList<>();


    /**
     *  Pomocná metoda pro generovaní ID
     * @return ID
     */
    private int generateId() {
        return portfolio.size() + 1;
    }

    /**
     *  Přidání nové kryptoměny do portfolia
     * @param dto data, která posílá uživatel
     */
    public void addCrypto(CryptoRequestDTO dto) {
        Crypto crypto = new Crypto(
                generateId(),
                dto.getName(),
                dto.getSymbol(),
                dto.getPrice(),
                dto.getQuantity()
        );

        portfolio.add(crypto);
    }

    /**
     *  Smazání kryptoměny z portfolia podle ID kryptoměny
     * @param id ID kryptoměny
     * @return true - když vymaz proběhl, false - když kryptoměna s takovým id nebyla nalezena
     */
    public boolean removeCrypto(int id) {
        return portfolio.removeIf(c -> c.getId() == id);
    }

    /**
     *  Výpis všech kryptoměn v portfoliu s možnistí žázení portfolia podle názvu, ceny nebo počtu jednotek.
     * @param sort možnost řázení
     * @return pokud je uveden paramert sort - vrací portfolio seřazené podle parametru. Pokud není parametr uveden - vrací neseřazený seznam.
     */
    public ArrayList<Crypto> getPortfolio(SortType sort) {
        ArrayList<Crypto> sortedList = new ArrayList<>(portfolio);

        Comparator<Crypto> comparator = switch (sort) {
            case PRICE -> Comparator.comparing(Crypto::getPrice);
            case NAME -> Comparator.comparing(Crypto::getName);
            case QUANTITY -> Comparator.comparing(Crypto::getQuantity);
            case null -> null;
        };

        if (comparator != null) {
            sortedList.sort(comparator);
        }

        return sortedList;
    }

    /**
     *  Ziskání kryptoměny podle ID.
     * @param id ID kryptoměny
     * @return kryptoměna
     */
    public Crypto getCryptoById(int id) {
        return portfolio.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Crypto not found"));
    }

    /**
     *  Aktualizace kryptoměny podle ID.
     * @param newCrypto aktualizovaná data kryptoměny
     * @param id ID kryptoměny
     * @return aktualizovaná kryptoměna
     */
    public Crypto updateCrypto(Crypto newCrypto, int id) {
        Crypto existingCrypto = getCryptoById(id);

        existingCrypto.setName(newCrypto.getName());
        existingCrypto.setSymbol(newCrypto.getSymbol());
        existingCrypto.setPrice(newCrypto.getPrice());
        existingCrypto.setQuantity(newCrypto.getQuantity());

        return existingCrypto;
    }

    /**
     *  Výpočet celkové hodnoty portfolia.
     * @return celková hodnota portfolia (součet price * quantity všech kryptoměn)
     */
    public BigDecimal getPortfolioValue() {
        return portfolio.stream()
                .map(crypto -> crypto.getPrice().multiply(BigDecimal.valueOf(crypto.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
