package com.trader.service.currencies;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyService {
    private final CurrencyRepository repository;

    CurrencyService(CurrencyRepository repository) {
        this.repository = repository;
    }

    public Currency create(String name, String abbreviation, Integer confirmations, Double fee, String baseAddress) {
        return this.save(new Currency(name, abbreviation, confirmations, fee, baseAddress));
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
