package com.trader.service.twitter;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Component
public class TwitterScrapper {
    public final static String URL = "https://twitter.com/";

    @Async
    public CompletableFuture<TwitterProfilePage> getProfilePage(String currencyName) {
        try {
            return CompletableFuture.completedFuture(new TwitterProfilePage(currencyName));
        } catch (IOException e) {
            //
        }

        return null;
    }
}
