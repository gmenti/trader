package com.trader.services.currencies;

import org.springframework.scheduling.annotation.Async;
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

    @Async
    public void async() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            //
        }

        System.out.println("kkkk");
    }
}
