package com.bitmovin.player.integrations.comscoreanalytics

import android.content.Context
import android.util.Log

import com.bitmovin.player.BitmovinPlayer
import com.comscore.Analytics
import com.comscore.PublisherConfiguration

object ComScoreAnalytics {
    private val TAG = "ComScoreAnalytics"
    private var started: Boolean = false

    /**
     * Starts the ComScoreAnalytics app level tracking
     *
     * @param configuration - ComScore configuration with your app publisher id, publisher secret, and application name
     * @param context       - Application Context
     */
    @Synchronized
    fun start(configuration: ComScoreConfiguration, context: Context) {
        if (!started) {
            val myPublisherConfig = PublisherConfiguration.Builder()
                .publisherId(configuration.publisherId)
                .publisherSecret(configuration.publisherSecret)
                .applicationName(configuration.applicationName)
                .build()
            Analytics.getConfiguration().addClient(myPublisherConfig)

            Analytics.start(context)
            started = true
        }
    }

    @Synchronized
    fun createComScoreStreamingAnalytics(bitmovinPlayer: BitmovinPlayer, metadata: ComScoreMetadata): ComScoreStreamingAnalytics {
        if (started) {
            return ComScoreStreamingAnalytics(bitmovinPlayer, metadata)
        } else {
            Log.e(TAG, "ComScoreStreamingAnalytics was not created. Must call start() first")
            throw ComScoreAnalyticsException("ComScoreStreamingAnalytics was not created. Must call start() first")
        }
    }
}
