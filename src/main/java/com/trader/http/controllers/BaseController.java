package com.trader.http.controllers;

import java.util.ArrayList;

import com.trader.bittrex.objects.BittrexCurrency;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trader.currencies.Currency;
import com.trader.bittrex.BittrexService;
import com.trader.bittrex.responses.Response;
import com.trader.currencies.CurrencyService;

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
}
