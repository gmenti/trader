package com.trader.services.bittrex;

import com.trader.services.bittrex.responses.CurrenciesResponse;
import com.trader.services.bittrex.responses.MarketSummariesResponse;
import com.trader.services.bittrex.responses.MarketsResponse;
import com.trader.services.bittrex.responses.TickerResponse;
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

    private final RestTemplate restTemplate;

    public BittrexService() {
        this.restTemplate = new RestTemplate();
    }

    private String addMarketParamToUrl(String url, String market) {
        return url + "?market=" + market;
    }

    public MarketsResponse getMarkets() {
        return restTemplate.getForObject(GET_MARKETS_URL, MarketsResponse.class);
    }

    public MarketSummariesResponse getMarketSummary(String market) {
        return restTemplate.getForObject(
                addMarketParamToUrl(GET_MARKET_SUMMARY_URL, market),
                MarketSummariesResponse.class
        );
    }

    public MarketSummariesResponse getMarketSummaries() {
        return restTemplate.getForObject(GET_MARKET_SUMMARIES_URL, MarketSummariesResponse.class);
    }

    public CurrenciesResponse getCurrencies() {
        return restTemplate.getForObject(GET_CURRENCIES_URL, CurrenciesResponse.class);
    }

    public TickerResponse getTicker(String market) {
        return restTemplate.getForObject(
                addMarketParamToUrl(GET_TICKER_URL, market),
                TickerResponse.class
        );
    }
}
