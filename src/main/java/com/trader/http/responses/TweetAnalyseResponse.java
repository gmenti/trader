package com.trader.http.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trader.service.tweet.Tweet;
import com.trader.service.twitter.Twitter;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

class CurrencyResponse {
    private final String name;
    private final String abbreviation;

    CurrencyResponse(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}

public class TweetAnalyseResponse {
    private String twitter;
    private String message;
    private int retweets;
    private int favorites;
    private int followers;
    private double percentageFavorites;
    private List<CurrencyResponse> currencies;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date createdAt;

    public TweetAnalyseResponse(Tweet tweet) {
        this.loadValues(tweet);
    }

    @Transactional(readOnly = true)
    protected void loadValues(Tweet tweet) {
        Twitter twitter = tweet.getTwitter();

        this.currencies = new LinkedList<>();
        twitter.getCurrencies().forEach(currency -> {
            this.currencies.add(new CurrencyResponse(currency.getName(), currency.getAbbreviation()));
        });

        this.message = tweet.getMessage();
        this.twitter = twitter.getSlug();
        this.retweets = tweet.getRetweets();
        this.favorites = tweet.getFavorites();
        this.followers = twitter.getFollowers();
        this.percentageFavorites = (double) tweet.getFavorites() / (double) twitter.getFollowers();
        this.createdAt = tweet.getCreatedAt();
    }

    public String getMessage() {
        return message;
    }

    public int getRetweets() {
        return retweets;
    }

    public int getFavorites() {
        return favorites;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public double getPercentageFavorites() {
        return percentageFavorites;
    }

    public String getTwitter() {
        return twitter;
    }

    public int getFollowers() {
        return followers;
    }

    public List<CurrencyResponse> getCurrencies() {
        return currencies;
    }
}
