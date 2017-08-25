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

    private Map<String, Twitter> mapTwitterBySlug(Iterable<Twitter> twitters) {
        Map<String, Twitter> mapBySlug = new HashMap<>();

        for (Twitter twitter : twitters) {
            mapBySlug.put(twitter.getSlug(), twitter);
        }

        return mapBySlug;
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

    public Twitter findOne(long id) {
        return this.repository.findOne(id);
    }

    public Map<String, Twitter> findAllMappedBySlug() {
        return this.mapTwitterBySlug(this.findAll());
    }
}
