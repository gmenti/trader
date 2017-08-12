package com.trader.bittrex.objects;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

public class BittrexMarket extends BittrexObject {
	public String marketCurrency;
	public String baseCurrency;
	public String marketCurrencyLong;
	public String baseCurrencyLong;
	public Double minTradeSize;
	public String marketName;
	public Boolean isActive;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	public Date created;

	@JsonCreator
	BittrexMarket(Map<String, Object> map) {
		super(map);
	}
}
