package com.trader.http.controllers;

import com.trader.services.bittrex.BittrexService;
import com.trader.services.bittrex.responses.Response;
import com.trader.services.currencies.Currency;
import com.trader.services.currencies.CurrencyService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class BaseController {
    private final BittrexService bittrexService;
    private final CurrencyService currencyService;

    BaseController(CurrencyService currencyService, BittrexService bittrexService) {
        this.currencyService = currencyService;
        this.bittrexService = bittrexService;
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

    /*@RequestMapping("/twitter")
    ArrayList<TwitterPage> twitter() {
        Iterable<Currency> currencies = this.currencyService.findAll();
        ArrayList<TwitterPage> pageDocuments = new ArrayList<>();

        currencies.forEach(currency -> {
            try {
                pageDocuments.add(new TwitterPage(currency.getTwitter()));
                System.out.println("Added page " + currency.getTwitter());
            } catch (IOException e) {
                System.out.println(currency.getName());
            }
        });

        return pageDocuments;

    }*/
}
