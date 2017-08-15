package com.trader.http.controllers;

import com.trader.bittrex.BittrexService;
import com.trader.bittrex.responses.Response;
import com.trader.currencies.Currency;
import com.trader.currencies.CurrencyService;
import com.trader.twitter.Page;
import com.trader.twitter.TwitterService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
class BaseController {
	private final BittrexService bittrexService;
	private final CurrencyService currencyService;
    private final TwitterService twitterService;

    BaseController(CurrencyService currencyService, BittrexService bittrexService, TwitterService twitterService) {
        this.currencyService = currencyService;
		this.bittrexService = bittrexService;
        this.twitterService = twitterService;
    }

	@RequestMapping("/bittrex/{market}")
	Response getTicker(@PathVariable("market") String market) {
		return bittrexService.getMarketHistory(market);
	}

	@RequestMapping("/bittrex")
	Response home() {
		return bittrexService.getCurrencies();
	}

	@RequestMapping("/currency")
	Iterable<Currency> listTest() {
		return currencyService.findAll();
	}

    @RequestMapping("/twitter")
    ArrayList<Page> twitter() {
        Iterable<Currency> currencies = this.currencyService.findAll();
        ArrayList<Page> pages = new ArrayList<>();

        currencies.forEach(currency -> {
            try {
                pages.add(twitterService.loadPage(currency.getTwitter()));
                System.out.println("Added page " + currency.getTwitter());
            } catch (IOException e) {
                System.out.println(currency.getName());
            }
        });

        return pages;
    }
}
