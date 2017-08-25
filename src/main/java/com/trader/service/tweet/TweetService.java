package com.trader.service.tweet;

import com.trader.service.twitter.Twitter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TweetService {
    private final TweetRepository repository;

    TweetService(TweetRepository repository) {
        this.repository = repository;
    }

    private Map<Long, Tweet> mapTweetsByUUID(Iterable<Tweet> tweets) {
        Map<Long, Tweet> tweetsMappedByUUID = new HashMap<>();

        tweets.forEach(tweet -> {
            tweetsMappedByUUID.put(tweet.getUUID(), tweet);
        });

        return tweetsMappedByUUID;
    }

    private Map<Long, Map<Long, Tweet>> mapByUUIDMappedByTwitterId(Map<Long, Tweet> tweetsMappedByUUID) {
        Map<Long, Map<Long, Tweet>> tweetsMappedByUUIDMappedByTwitterId = new HashMap<>();

        tweetsMappedByUUID.forEach((uuid, tweet) -> {
            Map<Long, Tweet> mapOfTweetsByUUID = tweetsMappedByUUIDMappedByTwitterId.getOrDefault(
                tweet.getTwitterId(),
                new HashMap<>()
            );

            mapOfTweetsByUUID.put(uuid, tweet);

            tweetsMappedByUUIDMappedByTwitterId.put(tweet.getTwitterId(), mapOfTweetsByUUID);
        });

        return tweetsMappedByUUIDMappedByTwitterId;
    }

    public Iterable<Tweet> findAll() {
        return this.repository.findAllByOrderByCreatedAtDesc();
    }

    public Page<Tweet> findAll(Pageable pageable) {
        return this.repository.findAllByOrderByCreatedAtDesc(pageable);
    }

    public Iterable<Tweet> findAllByTwitterId(long twitterId) {
        return this.repository.findAllByTwitterIdOrderByCreatedAtDesc(twitterId);
    }

    public Map<Long, Tweet> findAllMappedByUUID() {
        return this.mapTweetsByUUID(this.findAll());
    }

    public Map<Long, Map<Long, Tweet>> findAllMappedByUUIDMappedByTwitterId() {
        return this.mapByUUIDMappedByTwitterId(this.findAllMappedByUUID());
    }

    public Tweet save(Tweet tweet) {
        return this.repository.save(tweet);
    }

    public Tweet create(long uuid, Twitter twitter, String message, int retweets, int favorites, Date createdAt) {
        return this.save(new Tweet(uuid, twitter, message, retweets, favorites, createdAt));
    }
}
