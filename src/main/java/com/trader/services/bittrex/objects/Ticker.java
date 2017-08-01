package com.trader.services.bittrex.objects;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;

public class Ticker extends BaseObject {
    public Double bid;
    public Double ask;
    public Double last;

    @JsonCreator
    Ticker(Map<String, Object> map) {
        super(map);
    }
}
