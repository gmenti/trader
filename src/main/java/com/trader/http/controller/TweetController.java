package com.trader.http.controller;

import com.trader.service.tweet.Tweet;
import com.trader.service.tweet.TweetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tweet")
public class TweetController {
    private final TweetService tweetService;

    TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping
    public Page<Tweet> index(@RequestParam("page") int page) {
        return this.tweetService.findAll(new PageRequest(page, 20));
    }
}
