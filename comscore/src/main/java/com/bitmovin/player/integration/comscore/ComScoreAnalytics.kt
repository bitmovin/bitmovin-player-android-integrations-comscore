package com.bitmovin.player.integration.comscore

import android.content.Context

import com.bitmovin.player.BitmovinPlayer
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
     * Set user consent to [ComScoreUserConsent.GRANTED]
     *
     */
    @Deprecated("Deprecated as of release 1.3.0", replaceWith = ReplaceWith("setPersistentLabel(\"label\" to \"value\")"))
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
     * Set user consent to [ComScoreUserConsent.DENIED]
     *
     */
    @Deprecated("Deprecated as of release 1.3.0", replaceWith = ReplaceWith("setPersistentLabel(\"label\" to \"value\")"))
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
     * Set persistent labels on the ComScore [PublisherConfiguration]
     *
     * @param labels - the labels to set
     */
    @Synchronized
    fun setPersistentLabels(labels: List<Pair<String, String>>) = labels.forEach { setPersistentLabel(it) }

    /**
     * Set a persistent label on the ComScore [PublisherConfiguration]
     *
     * @param label - the label to apply
     */
    @Synchronized
    fun setPersistentLabel(label: Pair<String, String>) {
        if (isStarted) {
            val publisherConfig = Analytics.getConfiguration().getPublisherConfiguration(configuration.publisherId)
            publisherConfig.setPersistentLabel(label.first, label.second)
            Analytics.notifyHiddenEvent()
            BitLog.d("ComScore persistent label applied: [${label.first}:${label.second}")
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
