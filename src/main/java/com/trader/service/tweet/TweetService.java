package com.trader.service.tweet;

import com.trader.service.twitter.Twitter;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TweetService {
    private final TweetRepository repository;

    TweetService(TweetRepository repository) {
        this.repository = repository;
    }

    public Iterable<Tweet> findAll() {
        return this.repository.findAllByOrderByCreatedAtDesc();
    }

    public Tweet save(Tweet tweet) {
        return this.repository.save(tweet);
    }

    public Tweet create(long uuid, Twitter twitter, String message, int retweets, int favorites, Date createdAt) {
        return this.save(new Tweet(uuid, twitter, message, retweets, favorites, createdAt));
    }
}
