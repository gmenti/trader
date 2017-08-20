package com.trader.integration.bittrex.objects;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;

public class BittrexTicker extends BittrexObject {
    public Double bid;
    public Double ask;
    public Double last;

    @JsonCreator
    BittrexTicker(Map<String, Object> map) {
        super(map);
    }
}
