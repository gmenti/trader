package com.trader.scraper.coinmarketcap;

import com.trader.scraper.Sites;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class CurrencyDocument {
    private final String twitter;

    public CurrencyDocument(String currencyName) throws IOException {
        Document document = Jsoup
            .connect(Sites.COINMARKETCAP.getUrl() + "currencies/" + currencyName)
            .get();

        this.twitter = this.loadTwitter(document);
    }

    private String loadTwitter(Document document) {
        return document
            .getElementsByClass("twitter-timeline")
            .first()
            .attr("href")
            .replace(Sites.TWITTER.getUrl(), "");
    }

    public String getTwitter() {
        return this.twitter;
    }
}

