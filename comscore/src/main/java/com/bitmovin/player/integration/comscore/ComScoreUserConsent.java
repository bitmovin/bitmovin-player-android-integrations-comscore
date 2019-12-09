package com.bitmovin.player.integration.comscore;

public enum ComScoreUserConsent {
    NO("0"),
    YES("1"),
    UNKOWN("-1");

    private String value;

    ComScoreUserConsent(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
