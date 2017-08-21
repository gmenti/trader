package com.trader.http.controller;

import com.trader.integration.bittrex.BittrexIntegration;
import com.trader.integration.bittrex.responses.Response;
import com.trader.service.currency.Currency;
import com.trader.service.currency.CurrencyService;
import com.trader.service.tweet.Tweet;
import com.trader.service.tweet.TweetService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class BaseController {
    private final TweetService tweetService;
    private final BittrexIntegration bittrexIntegration;
    private final CurrencyService currencyService;

    BaseController(TweetService tweetService, CurrencyService currencyService, BittrexIntegration bittrexIntegration) {
        this.tweetService = tweetService;
        this.currencyService = currencyService;
        this.bittrexIntegration = bittrexIntegration;
    }

    @RequestMapping("/bittrex/{market}")
    Response getTicker(@PathVariable("market") String market) {
        return bittrexIntegration.getMarketHistory(market);
    }

    @RequestMapping("/bittrex")
    Response home() {
        return bittrexIntegration.getCurrencies();
    }

    @RequestMapping("/currency")
    Iterable<Currency> listTest() {
        return currencyService.findAll();
    }

    @RequestMapping("/tweets")
    Iterable<Tweet> tweets() {
        return tweetService.findAll();
    }
}
