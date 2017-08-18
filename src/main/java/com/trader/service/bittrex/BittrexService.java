package com.trader.service.bittrex;

import com.trader.service.bittrex.responses.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BittrexService {
    private final static String BASE_URI = "https://bittrex.com/api/";
    private final static String V1_URI = BASE_URI + "v1.1/";
    private final static String V1_PUBLIC_URI = V1_URI + "public/";
    private final static String GET_MARKETS_URL = V1_PUBLIC_URI + "getmarkets";
    private final static String GET_MARKET_SUMMARY_URL = V1_PUBLIC_URI + "getmarketsummary";
    private final static String GET_MARKET_SUMMARIES_URL = V1_PUBLIC_URI + "getmarketsummaries";
    private final static String GET_CURRENCIES_URL = V1_PUBLIC_URI + "getcurrencies";
    private final static String GET_TICKER_URL = V1_PUBLIC_URI + "getticker";
    private final static String GET_MARKET_HISTORY_URL = V1_PUBLIC_URI + "getmarkethistory";

    private final RestTemplate restTemplate;

    public BittrexService() {
        this.restTemplate = new RestTemplate();
    }

    private String addMarketParamToUrl(String url, String market) {
        return url + "?market=" + market;
    }

    public CurrenciesResponse getCurrencies() {
        return restTemplate.getForObject(GET_CURRENCIES_URL, CurrenciesResponse.class);
    }

    public MarketHistoryResponse getMarketHistory(String market) {
        return restTemplate.getForObject(addMarketParamToUrl(GET_MARKET_HISTORY_URL, market),
            MarketHistoryResponse.class);
    }

    public MarketsResponse getMarkets() {
        return restTemplate.getForObject(GET_MARKETS_URL, MarketsResponse.class);
    }

    public MarketSummariesResponse getMarketSummaries() {
        return restTemplate.getForObject(GET_MARKET_SUMMARIES_URL, MarketSummariesResponse.class);
    }

    public MarketSummariesResponse getMarketSummary(String market) {
        return restTemplate.getForObject(addMarketParamToUrl(GET_MARKET_SUMMARY_URL, market),
            MarketSummariesResponse.class);
    }

    public TickerResponse getTicker(String market) {
        return restTemplate.getForObject(addMarketParamToUrl(GET_TICKER_URL, market), TickerResponse.class);
    }
}
