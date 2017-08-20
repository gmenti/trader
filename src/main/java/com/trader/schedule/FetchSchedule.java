package com.trader.schedule;

import com.trader.fetcher.CurrencyFetcher;
import com.trader.integration.bittrex.BittrexIntegration;
import com.trader.service.currency.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
class FetchSchedule {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CurrencyService currencyService;
    private final BittrexIntegration bittrexIntegration;

    FetchSchedule(CurrencyService currencyService, BittrexIntegration bittrexIntegration) {
        this.currencyService = currencyService;
        this.bittrexIntegration = bittrexIntegration;
    }

    @Scheduled(fixedRate = 86400000) // 1 day
    protected void fetchCurrencies() {
        new CurrencyFetcher(currencyService, bittrexIntegration).run();
    }
}
