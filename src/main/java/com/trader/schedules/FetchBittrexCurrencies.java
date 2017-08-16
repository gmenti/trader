package com.trader.schedules;

import com.trader.scraper.coinmarketcap.CurrencyDocument;
import com.trader.services.bittrex.BittrexService;
import com.trader.services.bittrex.objects.BittrexCurrency;
import com.trader.services.currencies.Currency;
import com.trader.services.currencies.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
class FetchBittrexCurrencies implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final BittrexService bittrexService;
    private final CurrencyService currencyService;

    private FetchBittrexCurrencies(BittrexService bittrexService, CurrencyService currencyService) {
        this.bittrexService = bittrexService;
        this.currencyService = currencyService;
    }

    private Map<BittrexCurrency, String> getBittrexCurrenciesWithTwitter() {
        Map<BittrexCurrency, String> bittrexCurrenciesWithTwitter = new HashMap<>();

        for (BittrexCurrency bittrexCurrency : this.bittrexService.getCurrencies().getResult()) {
            try {
                String twitter = new CurrencyDocument(bittrexCurrency.currencyLong).getTwitter();

                if (twitter != null) {
                    bittrexCurrenciesWithTwitter.put(bittrexCurrency, twitter);
                }
            } catch (IOException e) {
                //
            }
        }

        return bittrexCurrenciesWithTwitter;
    }

    @Transactional
    protected void fetch(Map<BittrexCurrency, String> bittrexCurrenciesWithTwitter) {
        Map<String, Currency> mapByAbbreviation = this.currencyService.getCurrenciesMappedByAbbreviation();

        bittrexCurrenciesWithTwitter.forEach((BittrexCurrency bittrexCurrency, String twitter) -> {
            Currency currency = mapByAbbreviation.get(bittrexCurrency.currency);

            try {
                if (currency == null) {
                    this.currencyService.create(
                        twitter,
                        bittrexCurrency.currencyLong,
                        bittrexCurrency.currency,
                        bittrexCurrency.minConfirmation,
                        bittrexCurrency.txFee,
                        bittrexCurrency.baseAddress
                    );
                } else {
                    currency.setTwitter(twitter);
                    currency.setName(bittrexCurrency.currencyLong);
                    currency.setAbbreviation(bittrexCurrency.currency);
                    currency.setConfirmations(bittrexCurrency.minConfirmation);
                    currency.setFee(bittrexCurrency.txFee);
                    currency.setBaseAddress(bittrexCurrency.baseAddress);

                    this.currencyService.save(currency);
                }
            } catch (Exception e) {
                //
            }
        });
    }

    @Override
    @Scheduled(fixedRate = 86400000) // 1 day
    public void run() {
        logger.info("Getting bittrex currencies with twitter");
        Map<BittrexCurrency, String> bittrexCurrenciesWithTwitter = this.getBittrexCurrenciesWithTwitter();

        logger.info("Fetching currencies");
        this.fetch(bittrexCurrenciesWithTwitter);

        logger.info("Finished schedule");
    }
}
