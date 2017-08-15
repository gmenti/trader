package com.trader.twitter;

import org.jsoup.nodes.Element;

import java.util.Date;

class Tweet {
    private final long id;
    private final String author;
    private final Date createdAt;
    private final String message;
    private final int retweetsAmount;
    private final int favoritesAmount;

    Tweet(Element element) {
        this.id = this.loadId(element);
        this.author = this.loadAuthor(element);
        this.createdAt = this.loadCreatedAt(element);
        this.message = this.loadMessage(element);
        this.retweetsAmount = this.loadRetweetsAmount(element);
        this.favoritesAmount = this.loadLikesAmount(element);
    }

    private long loadId(Element element) {
        return Long.parseLong(
            element.attr("data-item-id")
        );
    }

    private String loadAuthor(Element element) {
        return element.select(".username > b").text();
    }

    private Date loadCreatedAt(Element element) {
        return new Date(
            Long.parseLong(
                element.select("._timestamp").attr("data-time-ms")
            )
        );
    }

    private String loadMessage(Element element) {
        return element.select(".TweetTextSize").text();
    }

    private int loadRetweetsAmount(Element element) {
        try {
            return Integer.parseInt(element
                .select(".ProfileTweet-action--retweet")
                .select(".ProfileTweet-actionCountForPresentation")
                .first()
                .text()
            );
        } catch (Exception e) {
            return 0;
        }
    }

    private int loadLikesAmount(Element element) {
        try {
            return Integer.parseInt(element
                .select(".ProfileTweet-action--favorite")
                .select(".ProfileTweet-actionCountForPresentation")
                .first()
                .text()
            );
        } catch (Exception e) {
            return 0;
        }
    }

    public long getId() {
        return this.id;
    }

    public String getAuthor() {
        return author;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getMessage() {
        return message;
    }

    public int getRetweetsAmount() {
        return retweetsAmount;
    }

    public int getFavoritesAmount() {
        return favoritesAmount;
    }
}
