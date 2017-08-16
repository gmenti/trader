package com.trader.schedules;

import com.trader.scraper.coinmarketcap.CurrencyDocument;
import com.trader.services.bittrex.BittrexService;
import com.trader.services.bittrex.objects.BittrexCurrency;
import com.trader.services.bittrex.responses.CurrenciesResponse;
import com.trader.services.currencies.Currency;
import com.trader.services.currencies.CurrencyService;
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
            Currency currency = mapByAbbreviation.get(bittrexCurrency.currency);

            try {
                if (currency == null) {
                    CurrencyDocument page = new CurrencyDocument(bittrexCurrency.currencyLong);

                    currency = new Currency(
                        page.getTwitter(),
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
            } catch (Exception e) {
                //
            }
        }
    }
}
