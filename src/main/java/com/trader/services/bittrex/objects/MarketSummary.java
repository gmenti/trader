package com.trader.services.bittrex.objects;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

public class MarketSummary extends BaseObject {
	public String marketName;
	public Double high;
	public Double low;
	public Double volume;
	public Double baseVolume;
	public Double bid;
	public Double ask;
	public Integer openBuyOrders;
	public Integer openSellOrders;
	public Double prevDay;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	public Date timeStamp;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	public Date created;

	@JsonCreator
	MarketSummary(Map<String, Object> map) {
		super(map);
	}
}
