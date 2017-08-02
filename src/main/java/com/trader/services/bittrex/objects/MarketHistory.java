package com.trader.services.bittrex.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Map;

public class MarketHistory extends BaseObject {
    public Integer id;
    public Double quantity;
    public Double price;
    public Double total;
    public String fillType;
    public String orderType;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public Date timeStamp;

    @JsonCreator
    MarketHistory(Map<String, Object> map) {
        super(map);
    }
}
