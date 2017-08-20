package com.trader.service.tweet;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TweetRepository extends CrudRepository<Tweet, Long> {
    //
}
