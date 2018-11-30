package com.bitmovin.player.integrations.comscoreanalytics;

public class ComScoreConfiguration {
    private String publisherId;
    private String publisherSecret;

    public ComScoreConfiguration(String publisherId, String publisherSecret) {
        this.publisherId = publisherId;
        this.publisherSecret = publisherSecret;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public String getPublisherSecret() {
        return publisherSecret;
    }
}