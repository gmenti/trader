package com.trader.fetcher;

import com.trader.service.coinmarketcap.CoinMarketCapCurrencyPage;
import com.trader.service.coinmarketcap.CoinMarketCapScrapper;
import com.trader.service.currencies.Currency;
import com.trader.service.currencies.CurrencyService;
import com.trader.service.twitter.TwitterProfilePage;
import com.trader.service.twitter.TwitterScrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
class TwitterFetcher implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CurrencyService currencyService;
    private final CoinMarketCapScrapper coinMarketCapServiceScraper;
    private final TwitterScrapper twitterScrapper;

    TwitterFetcher(CurrencyService currencyService, CoinMarketCapScrapper coinMarketCapServiceScraper, TwitterScrapper twitterScrapper) {
        this.currencyService = currencyService;
        this.coinMarketCapServiceScraper = coinMarketCapServiceScraper;
        this.twitterScrapper = twitterScrapper;
    }

    private void processTwitterProfilePage(TwitterProfilePage twitterProfilePage) {
        if (twitterProfilePage == null) {
            return;
        }


        logger.info("Future: " + twitterProfilePage.getFollowers());
    }

    private void processCoinMarketCapCurrencyPage(CoinMarketCapCurrencyPage coinMarketCapCurrencyPage) {
        if (coinMarketCapCurrencyPage == null) {
            return;
        }

        String twitterSlug = coinMarketCapCurrencyPage.getTwitter();

        if (twitterSlug.isEmpty()) {
            return;
        }

        logger.info("Get currency");

        this.twitterScrapper
            .getProfilePage(twitterSlug)
            .thenAccept(this::processTwitterProfilePage);
    }

    @Scheduled(fixedRate = 10000000) // 1s
    public void run() {

        for (Currency currency : this.currencyService.findAll()) {
            // TODO: Check if currency dont have twitter

            this.coinMarketCapServiceScraper
                .getCurrencyPage(currency.getName())
                .thenAccept(this::processCoinMarketCapCurrencyPage);
        }
    }
}
