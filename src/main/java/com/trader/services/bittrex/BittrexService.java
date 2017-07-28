package com.trader.services.bittrex;

import org.springframework.stereotype.Service;

@Service
public class BittrexService {
    private final String DEFAULT_URI = "TEST";

    public String getDefaultUri() {
        return DEFAULT_URI;
    }
}
