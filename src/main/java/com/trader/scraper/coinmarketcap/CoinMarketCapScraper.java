package com.trader.scraper.coinmarketcap;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Component
public class CoinMarketCapScraper {
    protected final static String URL = "https://coinmarketcap.com/";

    @Async
    public CompletableFuture<CoinMarketCapCurrency> getCurrency(String currencyName) throws IOException {
        return CompletableFuture.completedFuture(new CoinMarketCapCurrency(currencyName));
    }
}
