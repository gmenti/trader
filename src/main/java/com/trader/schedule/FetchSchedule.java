package com.trader.schedule;

import com.trader.fetcher.CurrencyFetcher;
import com.trader.service.bittrex.BittrexService;
import com.trader.service.currencies.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
class FetchSchedule {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CurrencyService currencyService;
    private final BittrexService bittrexService;

    FetchSchedule(CurrencyService currencyService, BittrexService bittrexService) {
        this.currencyService = currencyService;
        this.bittrexService = bittrexService;
    }

    @Scheduled(fixedRate = 86400000) // 1 day
    protected void fetchCurrencies() {
        new CurrencyFetcher(currencyService, bittrexService).run();
    }
}
