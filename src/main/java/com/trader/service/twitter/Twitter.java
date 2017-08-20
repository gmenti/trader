package com.trader.service.twitter;

import com.trader.service.tweet.Tweet;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class Twitter {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "twitter", fetch = FetchType.EAGER)
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
        this.slug = slug.toLowerCase();
        this.followers = followers;
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

    public Set<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(Set<Tweet> tweets) {
        this.tweets = tweets;
    }

    public Map<Long, Tweet> getTweetsMappedById() {
        Map<Long, Tweet> tweetsMappedById = new HashMap<>();
        Set<Tweet> tweets = this.getTweets();

        if (tweets == null) {
            return tweetsMappedById;
        }

        for (Tweet tweet : tweets) {
            tweetsMappedById.put(tweet.getId(), tweet);
        }

        return tweetsMappedById;
    }
}
