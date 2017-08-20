package com.trader.integration.bittrex.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Map;

public class BittrexCurrency extends BittrexObject {
    public String currency;
    public String currencyLong;
    public Integer minConfirmation;
    public Double txFee;
    public Boolean isActive;
    public String coinType;
    public String baseAddress;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public Date created;

    @JsonCreator
    BittrexCurrency(Map<String, Object> map) {
        super(map);
    }
}
