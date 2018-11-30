package com.bitmovin.player.integrations.comscoreanalytics;

import android.content.Context;

import com.comscore.Analytics;
import com.comscore.PublisherConfiguration;

public class ComScoreAnalytics {

    public static void addConfiguration(ComScoreConfiguration configuration) {
        PublisherConfiguration myPublisherConfig = new PublisherConfiguration.Builder()
                .publisherId(configuration.getPublisherId())
                .publisherSecret(configuration.getPublisherSecret()).build();
        Analytics.getConfiguration().addClient(myPublisherConfig);
    }

    public static void start(Context context) {
        Analytics.start(context);
    }

}
