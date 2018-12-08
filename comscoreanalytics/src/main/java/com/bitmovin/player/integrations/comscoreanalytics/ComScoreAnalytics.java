package com.bitmovin.player.integrations.comscoreanalytics;

import android.content.Context;

import com.comscore.Analytics;
import com.comscore.PublisherConfiguration;

public class ComScoreAnalytics {

    /**
     * Adds your ComScore provided publisher id, secret, and application name
     *
     * @param configuration
     */
    public static void addConfiguration(ComScoreConfiguration configuration) {
        PublisherConfiguration myPublisherConfig = new PublisherConfiguration.Builder()
                .publisherId(configuration.getPublisherId())
                .publisherSecret(configuration.getPublisherSecret())
                .applicationName(configuration.getApplicationName())
                .build();
        Analytics.getConfiguration().addClient(myPublisherConfig);
    }

    /**
     * Starts the ComScoreAnalytics app lifecycle tracking
     *
     * @param context - Application Context for your application
     */
    public static void start(Context context) {
        Analytics.start(context);
    }

}
