package com.trader.service.currency.schedule;

import com.trader.integration.bittrex.BittrexIntegration;
import com.trader.integration.bittrex.objects.BittrexCurrency;
import com.trader.integration.bittrex.responses.CurrenciesResponse;
import com.trader.service.currency.Currency;
import com.trader.service.currency.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Map;

@Component
class CurrencyFetcher implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CurrencyService currencyService;
    private final BittrexIntegration bittrexIntegration;

    private CurrencyFetcher(CurrencyService currencyService, BittrexIntegration bittrexIntegration) {
        this.currencyService = currencyService;
        this.bittrexIntegration = bittrexIntegration;
    }

    @Transactional
    protected void fetchCurrencies(ArrayList<BittrexCurrency> bittrexCurrencies) {
        Map<String, Currency> mapByAbbreviation = this.currencyService.findAllMappedByAbbreviation();

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
    @Scheduled(fixedRate = 86400000) // 1 day
    public void run() {
        long startedAt = System.currentTimeMillis();
        CurrenciesResponse response = this.bittrexIntegration.getCurrencies();

        if (response.getSuccess()) {
            this.fetchCurrencies(response.getResult());
            this.logger.info("Currencies fetched in " + (System.currentTimeMillis() - startedAt) + "ms");
        } else {
            this.logger.warn("An error occurred on get currencies of bittrex, trying again...");
            this.run();
        }
    }
}
