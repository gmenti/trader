package com.trader.service.tweet;

import com.trader.service.twitter.Twitter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
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

    public Iterable<Tweet> findAll() {
        return this.repository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public Iterable<Tweet> findAllWithCurrency() {
        Iterable<Tweet> tweets = this.repository.findAll();

        tweets.forEach(tweet -> System.out.println(tweet.getTwitter().getCurrencies().size()));

        return tweets;
    }

    public Iterable<Tweet> findAllByTwitterId(long twitterId) {
        return this.repository.findAllByTwitterId(twitterId);
    }

    public Iterable<Tweet> findAllOfLast24HoursOrderByCreatedAtDesc() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        return this.repository.findAllByCreatedAtAfterOrderByCreatedAtDesc(calendar.getTime());
    }

    @Transactional(readOnly = true)
    public Map<Long, Tweet> findAllMappedByUUID() {
        return this.mapTweetsByUUID(
            this.findAll()
        );
    }

    @Transactional(readOnly = true)
    public Map<Long, Map<Long, Tweet>> findAllMappedByUUIDMappedByTwitterId() {
        Map<Long, Map<Long, Tweet>> tweetsMappedByUUIDMappedByTwitterId = new HashMap<>();

        this.findAllMappedByUUID().forEach((uuid, tweet) -> {
            Map<Long, Tweet> tweetsMappedByUUID = tweetsMappedByUUIDMappedByTwitterId.getOrDefault(
                tweet.getTwitterId(),
                new HashMap<>()
            );

            tweetsMappedByUUID.put(uuid, tweet);
            tweetsMappedByUUIDMappedByTwitterId.put(tweet.getTwitterId(), tweetsMappedByUUID);
        });

        return tweetsMappedByUUIDMappedByTwitterId;
    }

    public Tweet save(Tweet tweet) {
        return this.repository.save(tweet);
    }

    public Tweet create(long uuid, Twitter twitter, String message, int retweets, int favorites, Date createdAt) {
        return this.save(new Tweet(uuid, twitter, message, retweets, favorites, createdAt));
    }
}
