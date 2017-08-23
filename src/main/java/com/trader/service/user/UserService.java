package com.trader.service.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;

    UserService(UserRepository repository) {
        this.repository = repository;
    }
}
