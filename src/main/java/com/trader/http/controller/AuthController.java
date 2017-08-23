package com.trader.http.controller;

import com.trader.http.request.CreateUserRequest;
import com.trader.service.user.User;
import com.trader.service.user.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final UserService userService;

    AuthController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public User create(@RequestBody CreateUserRequest request) {
        User user = this.userService.create(
            request.getEmail(),
            request.getPassword()
        );

        return user;
    }
}
