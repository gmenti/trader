package com.gmenti.trader.http.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
class BaseController {
	
	@RequestMapping("home")
	String home() {
		return "Hello World";
	}
	
}
