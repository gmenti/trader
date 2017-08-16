package com.trader.services.tweets;

import com.trader.services.twitter.Twitter;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TweetService {
    private final TweetRepository repository;

    TweetService(TweetRepository repository) {
        this.repository = repository;
    }

    public Tweet save(Tweet tweet) {
        return this.repository.save(tweet);
    }

    public Tweet create(long id, Twitter twitter, String message, int retweets, int favorites, Date createdAt) {
        return this.save(new Tweet(id, twitter, message, retweets, favorites, createdAt));
    }
}
