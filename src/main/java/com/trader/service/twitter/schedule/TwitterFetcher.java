package com.trader.service.twitter.schedule;

import com.trader.scrappe.coinmarketcap.CoinMarketCapScrapper;
import com.trader.scrappe.twitter.TweetElement;
import com.trader.scrappe.twitter.TwitterScrapper;
import com.trader.service.currency.Currency;
import com.trader.service.currency.CurrencyService;
import com.trader.service.tweet.Tweet;
import com.trader.service.tweet.TweetService;
import com.trader.service.twitter.Twitter;
import com.trader.service.twitter.TwitterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
class TwitterFetcher implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CurrencyService currencyService;
    private final TwitterService twitterService;
    private final TweetService tweetService;
    private final CoinMarketCapScrapper coinMarketCapServiceScraper;
    private final TwitterScrapper twitterScrapper;
    private ConcurrentMap<String, ArrayList<String>> currenciesAbbreviationsMappedByTwitter;

    TwitterFetcher(CurrencyService currencyService, TwitterService twitterService,
                   TweetService tweetService, CoinMarketCapScrapper coinMarketCapServiceScraper,
                   TwitterScrapper twitterScrapper) {
        this.currencyService = currencyService;
        this.twitterService = twitterService;
        this.tweetService = tweetService;
        this.coinMarketCapServiceScraper = coinMarketCapServiceScraper;
        this.twitterScrapper = twitterScrapper;
        this.currenciesAbbreviationsMappedByTwitter = new ConcurrentHashMap<>();
    }

    @Scheduled(initialDelay = 0, fixedRate = 43200000) // 12hrs
    private void loadCurrenciesAbbreviationsMappedByTwitter() {
        long startedAt = System.currentTimeMillis();
        int initialSize = this.currenciesAbbreviationsMappedByTwitter.size();

        int toLoadCount = 0;
        AtomicInteger loadedCount = new AtomicInteger();

        for (Currency currency : this.currencyService.findAll()) {
            toLoadCount++;

            this.coinMarketCapServiceScraper
                .getCurrencyPage(currency.getName())
                .thenAccept(coinMarketCapCurrencyPage -> {
                    if (coinMarketCapCurrencyPage != null && coinMarketCapCurrencyPage.getTwitter() != null) {
                        String twitter = coinMarketCapCurrencyPage.getTwitter();

                        ArrayList<String> abbreviations = this.currenciesAbbreviationsMappedByTwitter.getOrDefault(
                            twitter,
                            new ArrayList<>()
                        );

                        if (abbreviations.indexOf(currency.getAbbreviation()) == -1) {
                            abbreviations.add(currency.getAbbreviation());
                        }

                        this.currenciesAbbreviationsMappedByTwitter.put(
                            twitter,
                            abbreviations
                        );
                    }

                    loadedCount.incrementAndGet();
                }).exceptionally(e -> {
                e.printStackTrace();
                return null;
            });
        }

        while (toLoadCount != loadedCount.get()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //
            }
        }

        int addedAmount = this.currenciesAbbreviationsMappedByTwitter.size() - initialSize;

        logger.info("Added " + addedAmount + " new twitters to cache mapped by currency name in "
            + (System.currentTimeMillis() - startedAt) + "ms");

        if (addedAmount > 0) {
            this.loadCurrenciesAbbreviationsMappedByTwitter();
        }
    }

    @Scheduled(fixedDelay = 1) // 1ms
    public void run() {
        long startedAt = System.currentTimeMillis();

        Map<String, Twitter> twittersMappedBySlug = this.twitterService.findAllMappedBySlug();

        int countOfTwittersToProcess = this.currenciesAbbreviationsMappedByTwitter.size();
        AtomicInteger countOfTwittersProcessed = new AtomicInteger();

        this.currenciesAbbreviationsMappedByTwitter.forEach((twitterSlug, abbreviations) -> {
            this.twitterScrapper
                .getProfilePage(twitterSlug)
                .thenAccept(twitterProfilePage -> {
                    if (twitterProfilePage != null) {
                        Twitter twitter = twittersMappedBySlug.get(twitterSlug);

                        if (twitter == null) {
                            twitter = this.twitterService.create(
                                twitterProfilePage.getSlug(),
                                twitterProfilePage.getFollowers()
                            );

                            twittersMappedBySlug.put(twitter.getSlug(), twitter);
                        } else {
                            twitter.setFollowers(twitter.getFollowers());
                            this.twitterService.save(twitter);
                        }

                        Map<Long, Tweet> tweetsMappedById = twitter.tweetsMappedByUUID();

                        for (TweetElement tweetElement : twitterProfilePage.getTimeline()) {
                            Tweet tweet = tweetsMappedById.get(tweetElement.getId());

                            if (tweet == null) {
                                this.tweetService.create(
                                    tweetElement.getId(),
                                    twitter,
                                    tweetElement.getMessage(),
                                    tweetElement.getRetweetsAmount(),
                                    tweetElement.getFavoritesAmount(),
                                    tweetElement.getCreatedAt()
                                );
                            } else {
                                tweet.setMessage(tweetElement.getMessage());
                                tweet.setRetweets(tweetElement.getRetweetsAmount());
                                tweet.setFavorites(tweetElement.getFavoritesAmount());

                                this.tweetService.save(tweet);
                            }
                        }
                    }

                    countOfTwittersProcessed.incrementAndGet();
                });
        });

        while (countOfTwittersToProcess != countOfTwittersProcessed.get()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //
            }
        }

        logger.info("Fetched all twitters with tweets in " + (System.currentTimeMillis() - startedAt) + "ms");
    }
}
