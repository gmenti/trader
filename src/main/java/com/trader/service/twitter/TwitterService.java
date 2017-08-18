package com.trader.service.twitter;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TwitterService {
    private final TwitterRepository repository;

    TwitterService(TwitterRepository repository) {
        this.repository = repository;
    }

    public Twitter create(String slug, int followers) {
        return this.save(new Twitter(slug, followers));
    }

    public Twitter save(Twitter twitter) {
        return this.repository.save(twitter);
    }

    public Iterable<Twitter> findAll() {
        return this.repository.findAll();
    }

    public Map<String, Twitter> findAllMappedBySlug() {
        Map<String, Twitter> mapBySlug = new HashMap<>();

        for (Twitter twitter : this.findAll()) {
            mapBySlug.put(twitter.getSlug(), twitter);
        }

        return mapBySlug;
    }
}
