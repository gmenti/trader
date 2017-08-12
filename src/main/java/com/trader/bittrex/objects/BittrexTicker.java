package com.trader.bittrex.objects;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

public class BittrexTicker extends BittrexObject {
	public Double bid;
	public Double ask;
	public Double last;

	@JsonCreator
	BittrexTicker(Map<String, Object> map) {
		super(map);
	}
}
