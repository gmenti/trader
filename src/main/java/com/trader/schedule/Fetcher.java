package com.trader.schedule;

import com.trader.scraper.coinmarketcap.CoinMarketCapCurrency;
import com.trader.scraper.twitter.TweetElement;
import com.trader.scraper.twitter.TwitterPage;
import com.trader.service.bittrex.BittrexService;
import com.trader.service.bittrex.objects.BittrexCurrency;
import com.trader.service.currencies.Currency;
import com.trader.service.currencies.CurrencyService;
import com.trader.service.tweets.Tweet;
import com.trader.service.tweets.TweetService;
import com.trader.service.twitter.Twitter;
import com.trader.service.twitter.TwitterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Component
class Fetcher {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final BittrexService bittrexService;
    private final CurrencyService currencyService;
    private final TwitterService twitterService;
    private final TweetService tweetService;

    private Fetcher(BittrexService bittrexService, CurrencyService currencyService, TwitterService twitterService,
                    TweetService tweetService) {
        this.bittrexService = bittrexService;
        this.currencyService = currencyService;
        this.twitterService = twitterService;
        this.tweetService = tweetService;
    }

    @Scheduled(fixedRate = 86400000) // 1 day
    protected void fetchCurrencies() {
        long startedAt = System.currentTimeMillis();

        ArrayList<BittrexCurrency> bittrexCurrencies = this.bittrexService.getCurrencies().getResult();
        Map<String, Currency> mapByAbbreviation = this.currencyService.getCurrenciesMappedByAbbreviation();

        for (BittrexCurrency bittrexCurrency : bittrexCurrencies) {
            Currency currency = mapByAbbreviation.get(bittrexCurrency.currency);

            if (currency == null) {
                currency = this.currencyService.create(
                    bittrexCurrency.currencyLong,
                    bittrexCurrency.currency,
                    bittrexCurrency.minConfirmation,
                    bittrexCurrency.txFee,
                    bittrexCurrency.baseAddress
                );

                mapByAbbreviation.put(currency.getAbbreviation(), currency);
            } else {
                currency.setName(bittrexCurrency.currencyLong);
                currency.setAbbreviation(bittrexCurrency.currency);
                currency.setConfirmations(bittrexCurrency.minConfirmation);
                currency.setFee(bittrexCurrency.txFee);
                currency.setBaseAddress(bittrexCurrency.baseAddress);

                this.currencyService.save(currency);
            }
        }

        logger.info("Fetched currencies: " + (System.currentTimeMillis() - startedAt) + "ms");
    }

    // @Scheduled(fixedRate = 500) // 500ms
    protected void fetchTwitters() {
        long startedAt = System.currentTimeMillis();

        Iterable<Currency> currencies = this.currencyService.findAll();
        Map<String, Twitter> twittersMappedBySlug = this.twitterService.findAllMappedBySlug();

        for (Currency currency : currencies) {
            try {
                String twitterSlug = new CoinMarketCapCurrency(currency.getName()).getTwitter();

                if (twitterSlug == null) {
                    continue;
                }

                TwitterPage twitterPage = new TwitterPage(twitterSlug);
                Twitter twitter = twittersMappedBySlug.get(twitterSlug);

                if (twitter == null) {
                    twitter = this.twitterService.create(twitterSlug, twitterPage.getFollowers());
                    twittersMappedBySlug.put(twitter.getSlug(), twitter);

                    logger.info("Added twitter (" + twitter.getSlug() + ") for currency (" + currency.getName() + ")");
                } else {
                    twitter.setFollowers(twitterPage.getFollowers());
                    this.twitterService.save(twitter);
                }

                Map<Long, Tweet> tweetsMappedById = twitter.getTweetsMappedById();
                ArrayList<TweetElement> tweetElements = twitterPage.getTimeline();

                for (TweetElement tweetElement : tweetElements) {
                    Tweet tweet = tweetsMappedById.get(tweetElement.getId());

                    if (tweet == null) {
                        tweet = this.tweetService.create(
                            tweetElement.getId(),
                            twitter,
                            tweetElement.getMessage(),
                            tweetElement.getRetweetsAmount(),
                            tweetElement.getFavoritesAmount(),
                            tweetElement.getCreatedAt()
                        );

                        tweetsMappedById.put(tweet.getId(), tweet);
                        logger.info("Added tweet (" + tweetElement.getId() + ") for twitter (" + twitter.getSlug() + ")");
                    } else {
                        tweet.setMessage(tweetElement.getMessage());
                        tweet.setRetweets(tweetElement.getRetweetsAmount());
                        tweet.setFavorites(tweetElement.getFavoritesAmount());

                        this.tweetService.save(tweet);
                    }
                }
            } catch (IOException e) {
                //
            }
        }

        logger.info("Fetched twitters: " + (System.currentTimeMillis() - startedAt) + "ms");
    }
}
