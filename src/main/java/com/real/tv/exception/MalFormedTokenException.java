package com.real.tv.exception;

/**
 * Custom exception to handle mal formed token
 */
public class MalFormedTokenException extends RuntimeException {

    private static final String MAL_FORMED_TOKEN_MSG = "msg.mal-formed-token";

    public MalFormedTokenException() {
        super(MAL_FORMED_TOKEN_MSG);
    }
}
