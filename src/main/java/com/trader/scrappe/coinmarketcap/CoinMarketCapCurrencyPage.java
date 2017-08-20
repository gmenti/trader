package com.trader.scrappe.coinmarketcap;

import com.trader.scrappe.twitter.TwitterScrapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class CoinMarketCapCurrencyPage {
    private final String twitter;

    protected CoinMarketCapCurrencyPage(String currencyName) throws IOException {
        Document document = Jsoup
            .connect(CoinMarketCapScrapper.URL + "currencies/" + currencyName)
            .get();

        this.twitter = this.loadTwitter(document);
    }

    private String loadTwitter(Document document) {
        try {
            return document
                .getElementsByClass("twitter-timeline")
                .first()
                .attr("href")
                .replace(TwitterScrapper.URL, "")
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

