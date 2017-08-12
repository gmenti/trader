package com.trader.currencies.schedules;

import com.trader.bittrex.BittrexService;
import com.trader.bittrex.objects.BittrexCurrency;
import com.trader.bittrex.responses.CurrenciesResponse;
import com.trader.currencies.Currency;
import com.trader.currencies.CurrencyService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
class FetchBittrexCurrencies implements Runnable {
    private final BittrexService bittrexService;
    private final CurrencyService currencyService;

    private FetchBittrexCurrencies(BittrexService bittrexService, CurrencyService currencyService) {
        this.bittrexService = bittrexService;
        this.currencyService = currencyService;
    }

    @Override
    @Scheduled(fixedRate = 86400000) // 1 day
    public void run() {
        Iterable<Currency> currencies = this.currencyService.findAll();
        Map<String, Currency> mapByAbbreviation = new HashMap<>();

        for (Currency currency : currencies) {
            mapByAbbreviation.put(currency.getAbbreviation(), currency);
        }

        CurrenciesResponse response = this.bittrexService.getCurrencies();

        for (BittrexCurrency bittrexCurrency : response.getResult()) {
            Currency currency = mapByAbbreviation.get(bittrexCurrency.currencyLong);

            if (currency == null) {
                currency = new Currency(
                        bittrexCurrency.currencyLong,
                        bittrexCurrency.currency,
                        bittrexCurrency.minConfirmation,
                        bittrexCurrency.txFee,
                        bittrexCurrency.baseAddress
                );
            } else {
                currency.setName(bittrexCurrency.currencyLong);
                currency.setAbbreviation(bittrexCurrency.currency);
                currency.setConfirmations(bittrexCurrency.minConfirmation);
                currency.setFee(bittrexCurrency.txFee);
                currency.setBaseAddress(bittrexCurrency.baseAddress);
            }

            this.currencyService.save(currency);
        }
    }
}
