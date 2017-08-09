package com.trader.services.bittrex.objects;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Ticker extends BaseObject {
	public Double bid;
	public Double ask;
	public Double last;

	@JsonCreator
	Ticker(Map<String, Object> map) {
		super(map);
	}
}
