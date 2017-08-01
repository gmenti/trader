package com.trader.services.bittrex;

import com.trader.services.bittrex.responses.CurrenciesResponse;
import com.trader.services.bittrex.responses.MarketSummariesResponse;
import com.trader.services.bittrex.responses.MarketsResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BittrexService {
    private final static String BASE_URI = "https://bittrex.com/api/";
    private final static String GET_MARKETS_URL = BASE_URI + "v1.1/public/getmarkets";
    private final static String GET_MARKET_SUMMARIES_URL = BASE_URI + "v1.1/public/getmarketsummaries";
    private final static String GET_CURRENCIES_URL = BASE_URI + "v1.1/public/getcurrencies";

    private final RestTemplate restTemplate;

    public BittrexService() {
        this.restTemplate = new RestTemplate();
    }

    public MarketsResponse getMarkets() {
        return restTemplate.getForObject(GET_MARKETS_URL, MarketsResponse.class);
    }

    public MarketSummariesResponse getMarketSummaries() {
        return restTemplate.getForObject(GET_MARKET_SUMMARIES_URL, MarketSummariesResponse.class);
    }

    public CurrenciesResponse getCurrencies() {
        return restTemplate.getForObject(GET_CURRENCIES_URL, CurrenciesResponse.class);
    }
}
