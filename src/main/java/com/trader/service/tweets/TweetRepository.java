package com.trader.service.tweets;

import org.springframework.data.repository.CrudRepository;

interface TweetRepository extends CrudRepository<Tweet, Long> {
    //
}
