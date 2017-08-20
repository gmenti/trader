package com.trader.http.controller;

import com.trader.integration.bittrex.BittrexIntegration;
import com.trader.integration.bittrex.responses.Response;
import com.trader.service.currency.Currency;
import com.trader.service.currency.CurrencyService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class BaseController {
    private final BittrexIntegration bittrexIntegration;
    private final CurrencyService currencyService;

    BaseController(CurrencyService currencyService, BittrexIntegration bittrexIntegration) {
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
