package com.trader.twitter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
        // TODO: Load tweets of document

        return new ArrayList<Tweet>();
    }

    public int getFollowers() {
        return this.followers;
    }

    public ArrayList<Tweet> getTweets() {
        return this.tweets;
    }
}
