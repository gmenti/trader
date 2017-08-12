package com.trader.bittrex.objects;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

public class BittrexMarketHistory extends BittrexObject {
	public Integer id;
	public Double quantity;
	public Double price;
	public Double total;
	public String fillType;
	public String orderType;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	public Date timeStamp;

	@JsonCreator
	BittrexMarketHistory(Map<String, Object> map) {
		super(map);
	}
}
