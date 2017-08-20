package com.trader.scrappe.coinmarketcap;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
public class CoinMarketCapScrapper {
    public final static String URL = "https://coinmarketcap.com/";

    @Async
    public CompletableFuture<CoinMarketCapCurrencyPage> getCurrencyPage(String currencyName) {
        try {
            return CompletableFuture.completedFuture(new CoinMarketCapCurrencyPage(currencyName));
        } catch (IOException e) {
            //
        }

        return null;
    }
}
