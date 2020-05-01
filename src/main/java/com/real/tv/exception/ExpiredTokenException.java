package com.real.tv.exception;

/**
 * Custom exception to handle expired tokens
 */
public class ExpiredTokenException extends RuntimeException {

    private static final String EXPIRED_TOKEN_MSG = "msg.expired-token";

    public ExpiredTokenException() {
        super(EXPIRED_TOKEN_MSG);
    }
}
