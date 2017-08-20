package com.trader.service.tweet;

import com.trader.service.twitter.Twitter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Tweet {
    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name = "twitter_id")
    private Twitter twitter;

    @NotNull
    @Length(min = 0, max = 1000)
    private String message;

    @NotNull
    private int retweets;

    @NotNull
    private int favorites;

    @NotNull
    private Date createdAt;

    protected Tweet() {
        //
    }

    public Tweet(long id, Twitter twitter, String message, int retweets, int favorites, Date createdAt) {
        this.id = id;
        this.twitter = twitter;
        this.message = message;
        this.retweets = retweets;
        this.favorites = favorites;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public Twitter getTwitter() {
        return twitter;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRetweets() {
        return retweets;
    }

    public void setRetweets(int retweets) {
        this.retweets = retweets;
    }

    public int getFavorites() {
        return favorites;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
