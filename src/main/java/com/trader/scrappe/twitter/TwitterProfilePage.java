package com.trader.scrappe.twitter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

public class TwitterProfilePage {
    private final String slug;
    private final int followers;
    private final ArrayList<TweetElement> timeline;

    protected TwitterProfilePage(String pageName) throws IOException {
        Document document = Jsoup
            .connect(TwitterScrapper.URL + pageName)
            .get();

        this.slug = pageName;
        this.followers = this.loadFollowers(document);
        this.timeline = this.loadTimeline(document);
    }

    private int loadFollowers(Document document) {
        return Integer.parseInt(document
            .select(".ProfileNav-item--followers .ProfileNav-value")
            .attr("data-count")
        );
    }

    private ArrayList<TweetElement> loadTimeline(Document document) {
        ArrayList<TweetElement> timeline = new ArrayList<>();
        Element timeLine = document.getElementById("stream-items-id");

        if (timeLine != null) {
            timeLine.children().forEach((Element tweetElement) -> {
                timeline.add(new TweetElement(tweetElement));
            });
        }

        return timeline;
    }

    public String getSlug() {
        return this.slug;
    }

    public int getFollowers() {
        return this.followers;
    }

    public ArrayList<TweetElement> getTimeline() {
        return this.timeline;
    }
}
