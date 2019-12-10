package com.bitmovin.player.integration.comscore;

public enum ComScoreUserConsent {
    DENIED("0"),
    GRANTED("1"),
    UNKNOWN("-1");

    private String value;

    ComScoreUserConsent(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
