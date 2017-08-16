package com.trader.services.tweets;

import org.springframework.data.repository.CrudRepository;

interface TweetRepository extends CrudRepository<Tweet, Long> {
    //
}
