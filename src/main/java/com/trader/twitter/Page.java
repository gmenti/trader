package com.trader.twitter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

public class Page {
    private final static String BASE_URI = "https://twitter.com/";
    private final int followers;
    private final ArrayList<Tweet> tweets;

    public Page(String pageName) throws IOException {
        Document document = Jsoup
            .connect(BASE_URI + pageName)
            .get();

        this.followers = this.loadFollowers(document);
        this.tweets = this.loadTweets(document);
    }

    private int loadFollowers(Document document) {
        return Integer.parseInt(document
            .select(".ProfileNav-item--followers .ProfileNav-value")
            .attr("data-count")
        );
    }

    private ArrayList<Tweet> loadTweets(Document document) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        Element timeLine = document.getElementById("stream-items-id");

        timeLine.children().forEach((Element tweetElement) -> {
            tweets.add(new Tweet(tweetElement));
        });

        return tweets;
    }

    public int getFollowers() {
        return this.followers;
    }

    public ArrayList<Tweet> getTweets() {
        return this.tweets;
    }
}
