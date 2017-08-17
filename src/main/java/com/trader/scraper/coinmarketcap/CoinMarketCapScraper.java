package com.trader.scraper.coinmarketcap;

import com.trader.scraper.coinmarketcap.page.CoinMarketCapCurrency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Component
public class CoinMarketCapScraper {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Async
    public CompletableFuture<CoinMarketCapCurrency> currencyPage(String currencyName) throws IOException {
        return CompletableFuture.completedFuture(new CoinMarketCapCurrency(currencyName));
    }
}
