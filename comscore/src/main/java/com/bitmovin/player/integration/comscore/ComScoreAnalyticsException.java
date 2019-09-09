package com.bitmovin.player.integration.comscore;

public class ComScoreAnalyticsException extends RuntimeException {
    private String message;

    public ComScoreAnalyticsException() {
        super();
    }

    public ComScoreAnalyticsException(String message) {
        super(message);
        this.message = message;
    }
}
