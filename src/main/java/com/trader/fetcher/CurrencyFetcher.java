package com.trader.fetcher;

import com.trader.service.bittrex.BittrexService;
import com.trader.service.bittrex.objects.BittrexCurrency;
import com.trader.service.bittrex.responses.CurrenciesResponse;
import com.trader.service.currencies.Currency;
import com.trader.service.currencies.CurrencyService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Map;

public class CurrencyFetcher implements Runnable {
    private final CurrencyService currencyService;
    private final BittrexService bittrexService;

    public CurrencyFetcher(CurrencyService currencyService, BittrexService bittrexService) {
        this.currencyService = currencyService;
        this.bittrexService = bittrexService;
    }

    @Transactional
    protected void fetchCurrencies(ArrayList<BittrexCurrency> bittrexCurrencies) {
        Map<String, Currency> mapByAbbreviation = this.currencyService.getCurrenciesMappedByAbbreviation();

        for (BittrexCurrency bittrexCurrency : bittrexCurrencies) {
            Currency currency = mapByAbbreviation.get(bittrexCurrency.currency);

            if (currency == null) {
                currency = this.currencyService.create(
                    bittrexCurrency.currencyLong,
                    bittrexCurrency.currency,
                    bittrexCurrency.minConfirmation,
                    bittrexCurrency.txFee,
                    bittrexCurrency.baseAddress
                );

                mapByAbbreviation.put(currency.getAbbreviation(), currency);
            } else {
                currency.setName(bittrexCurrency.currencyLong);
                currency.setAbbreviation(bittrexCurrency.currency);
                currency.setConfirmations(bittrexCurrency.minConfirmation);
                currency.setFee(bittrexCurrency.txFee);
                currency.setBaseAddress(bittrexCurrency.baseAddress);

                this.currencyService.save(currency);
            }
        }
    }

    @Override
    public void run() {
        long startedAt = System.currentTimeMillis();

        CurrenciesResponse response = this.bittrexService.getCurrencies();

        if (response.getSuccess()) {
            this.fetchCurrencies(response.getResult());
        }
    }
}
