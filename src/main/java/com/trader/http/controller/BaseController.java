package com.trader.http.controller;

import com.trader.service.currency.Currency;
import com.trader.service.currency.CurrencyService;
import com.trader.service.tweet.Tweet;
import com.trader.service.tweet.TweetService;
import com.trader.service.twitter.Twitter;
import com.trader.service.twitter.TwitterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class BaseController {
    private final TweetService tweetService;
    private final TwitterService twitterService;
    private final CurrencyService currencyService;

    BaseController(TweetService tweetService, TwitterService twitterService, CurrencyService currencyService) {
        this.tweetService = tweetService;
        this.twitterService = twitterService;
        this.currencyService = currencyService;
    }

    @RequestMapping("/currency")
    Iterable<Currency> currency() {
        return currencyService.findAll();
    }

    @RequestMapping("/twitter")
    Iterable<Twitter> twitter() {
        return twitterService.findAll();
    }

    @RequestMapping("/tweets")
    Iterable<Tweet> tweets() {
        return tweetService.findAll();
    }
}
