package com.trader.services.currencies;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyService {
    private final Repository repository;

    CurrencyService(Repository repository) {
        this.repository = repository;
    }

    public Currency create(String twitter, String name, String abbreviation, Integer confirmations, Double fee, String baseAddress) throws IOException {
        return this.save(new Currency(twitter, name, abbreviation, confirmations, fee, baseAddress));
    }

    public Currency save(Currency currency) {
        return repository.save(currency);
    }

    public Iterable<Currency> findAll() {
        return repository.findAll();
    }

    public Map<String, Currency> getCurrenciesMappedByAbbreviation() {
        Map<String, Currency> mapByAbbreviation = new HashMap<>();

        for (Currency currency : this.findAll()) {
            mapByAbbreviation.put(currency.getAbbreviation(), currency);
        }

        return mapByAbbreviation;
    }
}
