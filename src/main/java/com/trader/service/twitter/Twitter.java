package com.trader.service.twitter;

import com.trader.service.currency.Currency;
import com.trader.service.tweet.Tweet;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Twitter {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private List<Currency> currencies;

    @OneToMany
    private List<Tweet> tweets;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public Map<Long, Tweet> tweetsMappedByUUID() {
        Map<Long, Tweet> tweetsMappedById = new HashMap<>();
        List<Tweet> tweets = this.getTweets();

        if (tweets != null) {
            for (Tweet tweet : tweets) {
                tweetsMappedById.put(tweet.getUUID(), tweet);
            }
        } else {
            System.out.println("IS NULL");
        }

        return tweetsMappedById;
    }
}
