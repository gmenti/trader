package com.trader.controllers;

import com.trader.services.bittrex.BittrexService;
import com.trader.services.bittrex.responses.Response;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
class BaseController {
	private final BittrexService bittrexService;

	public BaseController(BittrexService bittrexService) {
		this.bittrexService = bittrexService;
	}

	@RequestMapping("/bittrex")
	Response home() {
		return bittrexService.getCurrencies();
	}

	@RequestMapping("/bittrex/{market}")
	Response getTicker(@PathVariable("market") String market) {
		return bittrexService.getTicker(market);
	}
}
