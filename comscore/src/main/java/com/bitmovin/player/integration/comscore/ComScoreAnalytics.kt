package com.bitmovin.player.integration.comscore

import android.content.Context
import android.util.Log

import com.bitmovin.player.BitmovinPlayer
import com.comscore.Analytics
import com.comscore.PublisherConfiguration

object ComScoreAnalytics {
    /**
     * Returns true if the ComScoreAnalytics object has been started. You must do this prior to creating ComScoreStreamingAnalytics
     *
     * @return
     */
    private var isStarted: Boolean = false
    private lateinit var configuration: ComScoreConfiguration

    /**
     * Starts the ComScoreAnalytics app level tracking
     *
     * @param configuration - ComScore configuration with your app publisher id, publisher secret, and application name
     * @param context       - Application Context
     */
    @Synchronized
    fun start(configuration: ComScoreConfiguration, context: Context) {
        if (!isStarted) {
            this.configuration = configuration

            val builder = PublisherConfiguration.Builder()
                .publisherId(configuration.publisherId)
                .publisherSecret(configuration.publisherSecret)
                .secureTransmission(configuration.isSecureTransmissionEnabled)
                .applicationName(configuration.applicationName)

            with(configuration.userConsent) {
                if (this != ComScoreUserConsent.UNKNOWN) {
                    builder.persistentLabels(mapOf("cs_ucfr" to value))
                }
            }

            val publisherConfig = builder.build()

            Analytics.getConfiguration().addClient(publisherConfig)
            Analytics.start(context)

            isStarted = true
        }
    }

    /**
     * Sets the user consent to granted. Use after the ComScoreAnalytics object has been started
     */
    @Synchronized
    fun userConsentGranted() {
        if (isStarted) {
            val publisherConfig = Analytics.getConfiguration().getPublisherConfiguration(configuration.publisherId)
            publisherConfig.setPersistentLabel("cs_ucfr", ComScoreUserConsent.GRANTED.value)
            Analytics.notifyHiddenEvent()
            BitLog.d("ComScore user consent granted")
        }
    }

    /**
     * Sets the user consent to denied. Use after the ComScoreAnalytics object has been started
     */
    @Synchronized
    fun userConsentDenied() {
        if (isStarted) {
            val publisherConfig = Analytics.getConfiguration().getPublisherConfiguration(configuration.publisherId)
            publisherConfig.setPersistentLabel("cs_ucfr", ComScoreUserConsent.DENIED.value)
            Analytics.notifyHiddenEvent()
            BitLog.d("ComScore user consent denied")
        }
    }

    /**
     * Creates ComScoreStreamingAnalytics object that is attached to your bitmovin player
     *
     * @param bitmovinPlayer - the player to report on
     * @param metadata       - ComScoreMetadata associated with the current loaded source
     * @return ComScoreStreamingAnalytics object
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
