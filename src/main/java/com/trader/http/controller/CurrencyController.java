package com.trader.http.controller;

import com.trader.service.currency.Currency;
import com.trader.service.currency.CurrencyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("currency")
public class CurrencyController {
    private final CurrencyService currencyService;

    CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    public Iterable<Currency> index() {
        return this.currencyService.findAll();
    }
}
