package com.trader.services.bittrex.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Currency extends BaseObject {
    public String currency;
    public String currencyLong;
    public Integer minConfirmation;
    public Double txFee;
    public Boolean isActive;
    public String coinType;
    public String baseAddress;

    @JsonFormat(shape=JsonFormat.Shape.STRING)
    public Date created;

    @JsonCreator
    Currency(Map<String, Object> map) {
        super(map);
    }
}
