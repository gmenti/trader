package com.trader.controllers;

import com.trader.entities.Test;
import com.trader.services.bittrex.BittrexService;
import com.trader.services.bittrex.responses.Response;
import com.trader.services.test.TestService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class BaseController {
	private final BittrexService bittrexService;
    private final TestService testService;

    BaseController(TestService testService, BittrexService bittrexService) {
        this.testService = testService;
        this.bittrexService = bittrexService;
	}

    @RequestMapping("/test/create/{message}")
    Test test(@PathVariable("message") String message) {
        return testService.create(message);
    }

    @RequestMapping("/test/all")
    Iterable<Test> listTest() {
        return testService.findAll();
    }

	@RequestMapping("/bittrex")
	Response home() {
		return bittrexService.getCurrencies();
	}

	@RequestMapping("/bittrex/{market}")
	Response getTicker(@PathVariable("market") String market) {
        return bittrexService.getMarketHistory(market);
    }
}
