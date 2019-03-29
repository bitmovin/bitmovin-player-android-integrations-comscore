package com.bitmovin.player.integration.comscore;

import android.content.Context;
import android.util.Log;

import com.bitmovin.player.BitmovinPlayer;
import com.comscore.Analytics;
import com.comscore.PublisherConfiguration;

public class ComScoreAnalytics {
    private static final String TAG = "ComScoreAnalytics";
    private static boolean started;

    /**
     * Starts the ComScoreAnalytics app level tracking
     *
     * @param configuration - ComScore configuration with your app publisher id, publisher secret, and application name
     * @param context       - Application Context
     */
    public static synchronized void start(ComScoreConfiguration configuration, Context context) {
        if (!started) {
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

    /**
     * Creates ComScoreStreamingAnalytics object that is attached to your bitmovin player
     * @param bitmovinPlayer - the player to report on
     * @param metadata - ComScoreMetadata associated with the current loaded source
     * @return ComScoreStreamingAnalytics object
     */
    public static synchronized ComScoreStreamingAnalytics createComScoreStreamingAnalytics(BitmovinPlayer bitmovinPlayer, ComScoreMetadata metadata) {
        if (started) {
            return new ComScoreStreamingAnalytics(bitmovinPlayer, metadata);
        } else {
            Log.e(TAG, "ComScoreStreamingAnalytics was not created. Must call start() first");
            throw new ComScoreAnalyticsException("ComScoreStreamingAnalytics was not created. Must call start() first");
        }
    }

    /**
     * Returns true if the ComScoreAnalytics object has been started. You must do this prior to creating ComScoreStreamingAnalytics
     *
     * @return
     */
    public static boolean isStarted() {
        return started;
    }
}
