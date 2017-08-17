package com.trader.schedule;

import com.trader.scraper.coinmarketcap.CoinMarketCapScraper;
import com.trader.scraper.coinmarketcap.page.CoinMarketCapCurrency;
import com.trader.services.currencies.Currency;
import com.trader.services.currencies.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
class TwitterFetcher {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CurrencyService currencyService;
    private final CoinMarketCapScraper coinMarketCapScraper;

    TwitterFetcher(CurrencyService currencyService, CoinMarketCapScraper coinMarketCapScraper) {
        this.currencyService = currencyService;
        this.coinMarketCapScraper = coinMarketCapScraper;
    }

    @Async
    public void getCurrencyPage(String coinName) throws Exception {
        Thread.sleep(10000);
        logger.info("Future: " + coinName);
    }

    @Scheduled(fixedRate = 50000) // 500ms
    public void run() throws Exception {
        for (Currency currency : this.currencyService.findAll()) {
            CompletableFuture<CoinMarketCapCurrency> future = this.coinMarketCapScraper.currencyPage(currency.getName());

            future.thenAccept(coinMarketCapCurrency -> {
                logger.info("Future: " + coinMarketCapCurrency.getTwitter());
            }).exceptionally(e -> {
                logger.info("Exception");
                return null;
            });
        }
    }
}
