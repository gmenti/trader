package com.trader.services.bittrex.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Market {
    private static final SimpleDateFormat dateFormmatter = new SimpleDateFormat("yyyy-MM-dd");
    private String marketCurrency;
    private String baseCurrency;
    private String marketCurrencyLong;
    private String baseCurrencyLong;
    private Double minTradeSize;
    private String marketName;
    private Boolean isActive;

    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Date created;

    @JsonCreator
    public Market(Map<String, Object> market) throws ParseException {
        this.marketCurrency = (String) market.get("MarketCurrency");
        this.baseCurrency = (String) market.get("BaseCurrency");
        this.marketCurrencyLong = (String) market.get("MarketCurrencyLong");
        this.baseCurrencyLong = (String) market.get("BaseCurrencyLong");
        this.minTradeSize = (Double) market.get("MinTradeSize");
        this.marketName = (String) market.get("MarketName");
        this.isActive = (Boolean) market.get("IsActive");
        this.created = dateFormmatter.parse((String) market.get("Created"));
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public String getMarketCurrency() {
        return marketCurrency;
    }

    public String getBaseCurrencyLong() {
        return baseCurrencyLong;
    }

    public String getMarketCurrencyLong() {
        return marketCurrencyLong;
    }

    public String getMarketName() {
        return marketName;
    }

    public Double getMinTradeSize() {
        return minTradeSize;
    }

    public Boolean getActive() {
        return isActive;
    }

    public Date getCreated() {
        return created;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public void setMarketCurrency(String marketCurrency) {
        this.marketCurrency = marketCurrency;
    }

    public void setMarketCurrencyLong(String marketCurrencyLong) {
        this.marketCurrencyLong = marketCurrencyLong;
    }

    public void setBaseCurrencyLong(String baseCurrencyLong) {
        this.baseCurrencyLong = baseCurrencyLong;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public void setMinTradeSize(Double minTradeSize) {
        this.minTradeSize = minTradeSize;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
