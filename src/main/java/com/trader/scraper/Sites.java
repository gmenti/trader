package com.trader.scraper;

public enum Sites {
    TWITTER("https://twitter.com/"),
    COINMARKETCAP("https://coinmarketcap.com/");

    private final String url;

    Sites(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
