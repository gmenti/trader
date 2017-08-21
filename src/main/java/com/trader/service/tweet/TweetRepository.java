package com.trader.service.tweet;

import org.springframework.data.repository.CrudRepository;

interface TweetRepository extends CrudRepository<Tweet, Long> {
    Iterable<Tweet> findAllByOrderByCreatedAtDesc();
    Iterable<Tweet> findAllByTwitterId(long twitterId);
}
