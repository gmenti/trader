package com.trader.service.twitter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trader.service.currency.Currency;
import com.trader.service.tweet.Tweet;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Twitter {
    @Id
    @GeneratedValue
    private long id;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "twitter")
    private Set<Currency> currencies;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "twitter")
    private Set<Tweet> tweets;

    @NotNull
    @Column(unique = true)
    private String slug;

    @NotNull
    private int followers;

    protected Twitter() {
        //
    }

    Twitter(String slug, int followers) {
        this.setSlug(slug);
        this.setFollowers(followers);
    }

    public long getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug.toLowerCase();
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public Set<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(Set<Tweet> tweets) {
        this.tweets = tweets;
    }

    public Set<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Set<Currency> currencies) {
        this.currencies = currencies;
    }
}
