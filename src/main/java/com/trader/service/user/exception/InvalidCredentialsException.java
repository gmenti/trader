package com.trader.service.user.exception;

public class InvalidCredentialsException extends Exception {

    public InvalidCredentialsException() {
        super("Invalid credentials");
    }
}
