package com.bitmovin.player.integration.comscore

import android.content.Context
import android.util.Log

import com.bitmovin.player.BitmovinPlayer
import com.comscore.Analytics
import com.comscore.PublisherConfiguration

object ComScoreAnalytics {

    private val TAG = "ComScoreAnalytics"
    /**
     * Returns true if the ComScoreAnalytics object has been started. You must do this prior to creating ComScoreStreamingAnalytics
     *
     * @return
     */
    private var isStarted: Boolean = false

    /**
     * Starts the ComScoreAnalytics app level tracking
     *
     * @param configuration - ComScore configuration with your app publisher id, publisher secret, and application name
     * @param context       - Application Context
     */
    @Synchronized
    fun start(configuration: ComScoreConfiguration, context: Context) {
        if (!isStarted) {
            val myPublisherConfig = PublisherConfiguration.Builder()
                .publisherId(configuration.publisherId)
                .publisherSecret(configuration.publisherSecret)
                .applicationName(configuration.applicationName)
                .build()
            Analytics.getConfiguration().addClient(myPublisherConfig)

            Analytics.start(context)
            isStarted = true
        }
    }

    /**
     * Creates ComScoreStreamingAnalytics object that is attached to your bitmovin player
     * @param bitmovinPlayer - the player to report on
     * @param metadata - ComScoreMetadata associated with the current loaded source
     * @return ComScoreStreamingAnalytics object
     */
    @Synchronized
    fun createComScoreStreamingAnalytics(bitmovinPlayer: BitmovinPlayer, metadata: ComScoreMetadata): ComScoreStreamingAnalytics {
        if (isStarted) {
            return ComScoreStreamingAnalytics(bitmovinPlayer, metadata)
        } else {
            Log.e(TAG, "ComScoreStreamingAnalytics was not created. Must call start() first")
            throw ComScoreAnalyticsException("ComScoreStreamingAnalytics was not created. Must call start() first")
        }
    }
}
