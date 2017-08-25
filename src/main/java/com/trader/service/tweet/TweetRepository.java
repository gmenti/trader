package com.trader.service.tweet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

interface TweetRepository extends CrudRepository<Tweet, Long> {
    Iterable<Tweet> findAllByOrderByCreatedAtDesc();

    Page<Tweet> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Iterable<Tweet> findAllByTwitterIdOrderByCreatedAtDesc(long twitterId);
}
