package com.bitmovin.player.integration.comscore;

public class ComScoreConfiguration {
    private String publisherId;
    private String publisherSecret;
    private String applicationName;
    private ComScoreUserConsent userConsent = ComScoreUserConsent.UNKNOWN;

    public ComScoreConfiguration(String publisherId, String publisherSecret, String applicationName, ComScoreUserConsent userConsent) {
        this(publisherId, publisherSecret, applicationName);
        this.userConsent = userConsent;
    }

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

    public ComScoreUserConsent getUserConsent() {
        return userConsent;
    }
}
