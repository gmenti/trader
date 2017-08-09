package com.trader.controllers;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trader.entities.Currency;
import com.trader.services.bittrex.BittrexService;
import com.trader.services.bittrex.responses.Response;
import com.trader.services.currency.CurrencyService;

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
		Response<ArrayList<com.trader.services.bittrex.objects.Currency>> response = bittrexService.getCurrencies();

		response.getResult().forEach((com.trader.services.bittrex.objects.Currency currency) -> {
			currencyService.create(currency.currencyLong, currency.currency, currency.minConfirmation, currency.txFee,
					currency.baseAddress);
		});

		return currencyService.findAll();
	}
}
