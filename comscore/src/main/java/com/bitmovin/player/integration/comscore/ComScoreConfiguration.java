package com.bitmovin.player.integration.comscore;

public class ComScoreConfiguration {
    private String publisherId;
    private String publisherSecret;
    private String applicationName;

    public ComScoreConfiguration(String publisherId, String publisherSecret, String applicationName) {
        this.publisherId = publisherId;
        this.publisherSecret = publisherSecret;
        this.applicationName = applicationName;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public String getPublisherSecret() {
        return publisherSecret;
    }

    public String getApplicationName() {
        return applicationName;
    }

}