package com.trader.controllers;

import com.trader.services.bittrex.BittrexService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
class BaseController {
	private final BittrexService bittrexService;

	public BaseController(BittrexService bittrexService) {
		this.bittrexService = bittrexService;
	}

	@RequestMapping("/bittrex")
	String home() {
		return bittrexService.getDefaultUri();
	}
}
