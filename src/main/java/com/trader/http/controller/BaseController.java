package com.trader.http.controller;

import com.trader.http.responses.TweetAnalyseResponse;
import com.trader.service.currency.Currency;
import com.trader.service.currency.CurrencyService;
import com.trader.service.tweet.Tweet;
import com.trader.service.tweet.TweetService;
import com.trader.service.twitter.Twitter;
import com.trader.service.twitter.TwitterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

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

    @RequestMapping("/analyse")
    List<TweetAnalyseResponse> analyse() {
        List<TweetAnalyseResponse> analyseResponses = new LinkedList<>();

        tweetService.findAll().forEach(tweet -> {
            Twitter twitter = tweet.getTwitter();

            twitter.getCurrencies().forEach(currency -> {
                analyseResponses.add(new TweetAnalyseResponse(tweet, twitter, currency));
            });
        });

        return analyseResponses;
    }
}
