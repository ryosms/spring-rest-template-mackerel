package com.ryosms.mackerel.service;

public class MackerelApiException extends RuntimeException {
    public MackerelApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
