package com.trader.http.controller;

import com.trader.service.tweet.Tweet;
import com.trader.service.tweet.TweetService;
import com.trader.service.twitter.Twitter;
import com.trader.service.twitter.TwitterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("twitter")
public class TwitterController {
    private final TwitterService twitterService;
    private final TweetService tweetService;

    TwitterController(TwitterService twitterService, TweetService tweetService) {
        this.twitterService = twitterService;
        this.tweetService = tweetService;
    }

    @GetMapping
    public Iterable<Twitter> index() {
        return this.twitterService.findAll();
    }

    @GetMapping("{id}")
    public Twitter show(@PathVariable("id") long id) {
        return this.twitterService.findOne(id);
    }

    @GetMapping("{id}/tweets")
    public Iterable<Tweet> tweets(@PathVariable("id") long id) {
        return this.tweetService.findAllByTwitterId(id);
    }
}
