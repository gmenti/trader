package com.trader.scraper.coinmarketcap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class CoinMarketCapCurrency {
    private final String twitter;

    protected CoinMarketCapCurrency(String currencyName) throws IOException {
        Document document = Jsoup
            .connect(CoinMarketCapScraper.URL + "currencies/" + currencyName)
            .get();

        this.twitter = this.loadTwitter(document);
    }

    private String loadTwitter(Document document) {
        try {
            return document
                .getElementsByClass("twitter-timeline")
                .first()
                .attr("href")
                .replace(CoinMarketCapScraper.URL, "")
                .toLowerCase();
        } catch (NullPointerException error) {
            //
        }

        return null;
    }

    public String getTwitter() {
        return this.twitter;
    }
}

