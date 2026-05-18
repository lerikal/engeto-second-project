package com.engeto.engeto_second_project.service;

import com.engeto.engeto_second_project.dto.CryptoRequestDTO;
import com.engeto.engeto_second_project.dto.CryptoResponseDTO;
import com.engeto.engeto_second_project.dto.CryptoUpdateDTO;
import com.engeto.engeto_second_project.model.Crypto;
import com.engeto.engeto_second_project.model.SortType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CryptoService {

    // Vytvořte ArrayList typuCrypto, který bude uchovávat seznam kryptoměn v portfoliu.
    private final ArrayList<Crypto> portfolio = new ArrayList<>();

    /**
     * Převede Crypto entitu na DTO.
     *
     * @param crypto entita kryptoměny určená ke konverzi
     * @return DTO reprezentace kryptoměny
     */
    private CryptoResponseDTO toDTO(Crypto crypto) {
        CryptoResponseDTO dto = new CryptoResponseDTO();
        dto.setId(crypto.getId());
        dto.setName(crypto.getName());
        dto.setSymbol(crypto.getSymbol());
        dto.setPrice(crypto.getPrice());
        dto.setQuantity(crypto.getQuantity());
        return dto;
    }

    // Metody ze zadaní

    /**
     *  Přidá novou kryptoměnu do portfolia
     *
     * @param dto vstupní data nové kryptoměny
     */
    public void addCrypto(CryptoRequestDTO dto) {
        Crypto crypto = new Crypto(
                UUID.randomUUID(),
                dto.getName(),
                dto.getSymbol(),
                dto.getPrice(),
                dto.getQuantity()
        );

        portfolio.add(crypto);
    }

    /**
     * Smaze kryptoměnu z portfolia jejího unikátního identifikátoru.
     *
     * @param id unikátní identifikátor kryptoměny
     * @return true pokud byla kryptoměna úspěšně smazána, false pokud nebyla nalezena
     */
    public boolean removeCrypto(UUID id) {
        return portfolio.removeIf(c -> c.getId().equals(id));
    }

    /**
     * Vrací seznam kryptoměn v portfoliu.
     * Seznam může být volitelně seřazen podle názvu, ceny nebo množství.
     *
     * @param sort typ řazení (PRICE, NAME, QUANTITY), nebo null pro neseřazený výstup
     * @return seznam kryptoměn převedených do DTO
     */
    public ArrayList<CryptoResponseDTO> getPortfolio(String sort) {
        ArrayList<Crypto> sortedList = new ArrayList<>(portfolio);
        SortType sortType = SortType.from(sort);

        Comparator<Crypto> comparator = switch (SortType.from(sort)) {
            case PRICE -> Comparator.comparing(Crypto::getPrice);
            case NAME -> Comparator.comparing(Crypto::getName);
            case QUANTITY -> Comparator.comparing(Crypto::getQuantity);
            case null -> null;
        };

        if (comparator != null) {
            sortedList.sort(comparator);
        }

        return sortedList.stream()
                .map(this::toDTO)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     *  Vyhledá kryptoměnu podle jejího unikátního identifikátoru (UUID).
     * @param id unikátní identifikátor kryptoměny
     * @return nalezená kryptoměna
     * @throws ResponseStatusException pokud kryptoměna s daným ID neexistuje (HTTP 404)
     */
    public Crypto getCryptoById(UUID id) {
        return portfolio.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Crypto not found"));
    }

    /**
     * Vyhledá kryptoměnu podle jejího ID a převede ji na DTO reprezentaci.
     * @param id unikátní identifikátor kryptoměny
     * @return DTO reprezentace nalezené kryptoměny
     */
    public CryptoResponseDTO getCryptoByIdDTO(UUID id) {
        Crypto crypto = getCryptoById(id);
        return toDTO(crypto);
    }

    /**
     * Aktualizuje kryptoměnu podle jejího unikátního identifikátoru.
     * @param dto nová data kryptoměny
     * @param id unikátní identifikátor kryptoměny
     * @return aktualizovaná kryptoměna ve formě DTO
     */
    public CryptoResponseDTO updateCrypto(CryptoUpdateDTO dto, UUID id) {
        Crypto existingCrypto = getCryptoById(id);

        existingCrypto.setName(dto.getName());
        existingCrypto.setSymbol(dto.getSymbol());
        existingCrypto.setPrice(dto.getPrice());
        existingCrypto.setQuantity(dto.getQuantity());

        return toDTO(existingCrypto);
    }

    /**
     *  Vypočítá celkovou hodnotu portfolia.
     *
     * @return součet hodnot všech kryptoměn (cena * množství)
     */
    public BigDecimal getPortfolioValue() {
        return portfolio.stream()
                .map(crypto -> crypto.getPrice().multiply(BigDecimal.valueOf(crypto.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
