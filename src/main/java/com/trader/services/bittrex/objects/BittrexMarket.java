package com.trader.services.bittrex.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Map;

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
