package com.trader.twitter;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TwitterService {

    public Page loadPage(String pageName) throws IOException {
        return new Page(pageName);
    }
}
