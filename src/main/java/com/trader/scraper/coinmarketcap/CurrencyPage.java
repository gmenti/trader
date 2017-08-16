package com.trader.scraper.coinmarketcap;

import com.trader.scraper.Sites;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class CurrencyPage {
    private final String twitter;

    public CurrencyPage(String currencyName) throws IOException {
        Document document = Jsoup
            .connect(Sites.COINMARKETCAP.getUrl() + "currencies/" + currencyName)
            .get();

        this.twitter = this.loadTwitter(document);
    }

    private String loadTwitter(Document document) {
        try {
            return document
                .getElementsByClass("twitter-timeline")
                .first()
                .attr("href")
                .replace(Sites.TWITTER.getUrl(), "")
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

