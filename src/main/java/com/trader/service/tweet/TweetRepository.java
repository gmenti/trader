package com.trader.service.tweet;

import org.springframework.data.repository.CrudRepository;

import java.util.Date;

interface TweetRepository extends CrudRepository<Tweet, Long> {
    Iterable<Tweet> findAllByCreatedAtAfterOrderByCreatedAtDesc(Date createdAt);
    Iterable<Tweet> findAllByOrderByCreatedAtDesc();
    Iterable<Tweet> findAllByTwitterId(long twitterId);
}
