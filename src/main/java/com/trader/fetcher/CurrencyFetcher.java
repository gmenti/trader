package com.trader.fetcher;

import com.trader.integration.bittrex.BittrexIntegration;
import com.trader.integration.bittrex.objects.BittrexCurrency;
import com.trader.integration.bittrex.responses.CurrenciesResponse;
import com.trader.service.currency.Currency;
import com.trader.service.currency.CurrencyService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Map;

public class CurrencyFetcher implements Runnable {
    private final CurrencyService currencyService;
    private final BittrexIntegration bittrexIntegration;

    public CurrencyFetcher(CurrencyService currencyService, BittrexIntegration bittrexIntegration) {
        this.currencyService = currencyService;
        this.bittrexIntegration = bittrexIntegration;
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
        CurrenciesResponse response = this.bittrexIntegration.getCurrencies();

        if (response.getSuccess()) {
            this.fetchCurrencies(response.getResult());
        } else {
            this.run();
        }
    }
}
