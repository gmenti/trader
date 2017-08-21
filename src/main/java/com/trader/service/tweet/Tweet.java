package com.trader.service.tweet;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trader.service.twitter.Twitter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id", "uuid"}),
})
public class Tweet {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private long UUID;

    @ManyToOne
    private Twitter twitter;

    @NotNull
    @Length(min = 0, max = 1000)
    private String message;

    @NotNull
    private int retweets;

    @NotNull
    private int favorites;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date createdAt;

    protected Tweet() {
        //
    }

    public Tweet(long UUID, Twitter twitter, String message, int retweets, int favorites, Date createdAt) {
        this.UUID = UUID;
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

    public void setId(long id) {
        this.id = id;
    }

    public long getUUID() {
        return UUID;
    }

    public void setUUID(long UUID) {
        this.UUID = UUID;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setTwitter(Twitter twitter) {
        this.twitter = twitter;
    }
}
