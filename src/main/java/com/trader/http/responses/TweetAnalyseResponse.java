package com.trader.http.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trader.service.currency.Currency;
import com.trader.service.tweet.Tweet;
import com.trader.service.twitter.Twitter;

import java.util.Date;

public class TweetAnalyseResponse {
    private final String name;
    private final String abbreviation;
    private final String twitter;
    private final String message;
    private final int retweets;
    private final int favorites;
    private final int followers;
    private final double percentageFavorites;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final Date createdAt;

    public TweetAnalyseResponse(Tweet tweet, Twitter twitter, Currency currency) {
        this.name = currency.getName();
        this.abbreviation = currency.getAbbreviation();
        this.message = tweet.getMessage();
        this.twitter = twitter.getSlug();
        this.retweets = tweet.getRetweets();
        this.favorites = tweet.getFavorites();
        this.followers = twitter.getFollowers();
        this.percentageFavorites = (double) tweet.getFavorites() / (double) twitter.getFollowers();
        this.createdAt = tweet.getCreatedAt();
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
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
}
