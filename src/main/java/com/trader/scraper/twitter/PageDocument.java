package com.trader.scraper.twitter;

import com.trader.scraper.Sites;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

public class PageDocument {
    private final int followers;
    private final ArrayList<Tweet> tweets;

    public PageDocument(String pageName) throws IOException {
        Document document = Jsoup
            .connect(Sites.TWITTER.getUrl() + pageName)
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

        if (timeLine != null) {
            timeLine.children().forEach((Element tweetElement) -> {
                tweets.add(new Tweet(tweetElement));
            });
        }

        return tweets;
    }

    public int getFollowers() {
        return this.followers;
    }

    public ArrayList<Tweet> getTweets() {
        return this.tweets;
    }
}
