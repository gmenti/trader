package com.trader.services.bittrex;

import com.trader.services.bittrex.responses.MarketsResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BittrexService {
    private final static String BASE_URI = "https://bittrex.com/api/v1.1/";
    private final RestTemplate restTemplate;

    public BittrexService() {
        this.restTemplate = new RestTemplate();
    }

    public MarketsResponse getMarkets() {
        return restTemplate.getForObject(BASE_URI + "/public/getmarkets", MarketsResponse.class);
    }
}
