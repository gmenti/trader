package com.trader.scraper.coinmarketcap;

import com.trader.twitter.TwitterService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class CurrencyPage {
    private final static String BASE_URI = "https://coinmarketcap.com/currencies/";
    private final String twitter;

    public CurrencyPage(String currencyName) throws IOException {
        Document document = Jsoup
            .connect(BASE_URI + currencyName)
            .get();

        this.twitter = this.loadTwitter(document);
    }

    private String loadTwitter(Document document) {
        return document
            .getElementsByClass("twitter-timeline")
            .first()
            .attr("href")
            .replace(TwitterService.BASE_URI, "");
    }

    public String getTwitter() {
        return this.twitter;
    }
}

