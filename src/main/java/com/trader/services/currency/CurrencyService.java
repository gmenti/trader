package com.trader.services.currency;

import com.trader.entities.Currency;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {
    private final Repository repository;

    CurrencyService(Repository repository) {
        this.repository = repository;
    }

    public Currency create(
            String name,
            String abbreviation,
            Integer confirmations,
            Double fee,
            String baseAddress
    ) {
        Currency currency = new Currency();
        currency.setName(name);
        currency.setAbbreviation(abbreviation);
        currency.setConfirmations(confirmations);
        currency.setFee(fee);
        currency.setBaseAddress(baseAddress);

        return repository.save(currency);
    }

    public Iterable<Currency> findAll() {
        return repository.findAll();
    }
}
