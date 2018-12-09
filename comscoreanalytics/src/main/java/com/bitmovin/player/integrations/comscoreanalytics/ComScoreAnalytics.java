package com.bitmovin.player.integrations.comscoreanalytics;

import android.content.Context;

import com.comscore.Analytics;
import com.comscore.PublisherConfiguration;

public class ComScoreAnalytics {
    private static boolean started;

    /**
     * Starts the ComScoreAnalytics app level tracking
     *
     * @param configuration - ComScore configuration with your app publisher id, publisher secret, and application name
     * @param context - Application Context
     */
    public static synchronized void start(ComScoreConfiguration configuration, Context context) {
        if(!started) {
            PublisherConfiguration myPublisherConfig = new PublisherConfiguration.Builder()
                    .publisherId(configuration.getPublisherId())
                    .publisherSecret(configuration.getPublisherSecret())
                    .applicationName(configuration.getApplicationName())
                    .build();
            Analytics.getConfiguration().addClient(myPublisherConfig);

            Analytics.start(context);
            started = true;
        }
    }
}
