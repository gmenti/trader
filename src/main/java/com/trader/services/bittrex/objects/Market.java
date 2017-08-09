package com.trader.services.bittrex.objects;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Market extends BaseObject {
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
	Market(Map<String, Object> map) {
		super(map);
	}
}
