package com.bitmovin.player.integration.comscore

import android.content.Context

import com.bitmovin.player.BitmovinPlayer
import com.bitmovin.player.integration.comscore.util.notifyHiddenEvent
import com.bitmovin.player.integration.comscore.util.notifyHiddenEvents
import com.comscore.Analytics
import com.comscore.PublisherConfiguration

object ComScoreAnalytics {

    var isStarted: Boolean = false
    private lateinit var configuration: ComScoreConfiguration

    /**
     * Start [ComScoreAnalytics] app level tracking
     *
     * @param configuration - [ComScoreConfiguration] with app publisher id, publisher secret, and application name
     * @param context       - Application context
     */
    @Synchronized
    fun start(configuration: ComScoreConfiguration, context: Context) {
        if (!isStarted) {
            this.configuration = configuration

            val publisherConfig = PublisherConfiguration.Builder().apply {
                publisherId(configuration.publisherId)
                secureTransmission(configuration.secureTransmission)

                // Only set user consent value if not equal to UNKNOWN
                if (configuration.userConsent != ComScoreUserConsent.UNKNOWN) {
                    persistentLabels(mapOf("cs_ucfr" to configuration.userConsent.value))
                }

            }.build()

            with(Analytics.getConfiguration()) {
                addClient(publisherConfig)
                setApplicationName(configuration.applicationName)

                if (configuration.childDirectedAppMode) {
                    enableChildDirectedApplicationMode()
                }
            }
            Analytics.start(context)

            isStarted = true
        }
    }

    /**
     * Set persistent labels on the ComScore [PublisherConfiguration]
     *
     * @param labels - the labels to set
     */
    @Synchronized
    fun setPersistentLabels(labels: Map<String, String>) {
        if (isStarted) {
            notifyHiddenEvents(configuration.publisherId, labels)
            BitLog.d("ComScore persistent labels set: ${labels.map { "${it.key}:${it.value}" }}")
        }
    }

    /**
     * Set a persistent label on the ComScore [PublisherConfiguration]
     *
     * @param label - the label name
     * @param value - the label value
     */
    @Synchronized
    fun setPersistentLabel(label: String, value: String) {
        if (isStarted) {
            notifyHiddenEvent(configuration.publisherId, label, value)
            BitLog.d("ComScore persistent label set: [$label:$value]")
        }
    }

    /**
     * Create [ComScoreStreamingAnalytics] object
     *
     * @param bitmovinPlayer - the [BitmovinPlayer] to report on
     * @param metadata       - [ComScoreMetadata] associated with the current loaded source
     * @return [ComScoreStreamingAnalytics] object
     */
    @Synchronized
    fun createComScoreStreamingAnalytics(bitmovinPlayer: BitmovinPlayer, metadata: ComScoreMetadata): ComScoreStreamingAnalytics {
        if (isStarted) {
            return ComScoreStreamingAnalytics(bitmovinPlayer, configuration, metadata)
        } else {
            BitLog.e("ComScoreStreamingAnalytics was not created. Must call start() first")
            throw ComScoreAnalyticsException("ComScoreStreamingAnalytics was not created. Must call start() first")
        }
    }
}
