package com.bitmovin.player.integration.comscore

import com.bitmovin.player.BitmovinPlayer
import com.comscore.PublisherConfiguration

/**
 * ComScoreStreaming analytics measures video playback and reports back to ComScore.
 *
 * @param bitmovinPlayer - the [BitmovinPlayer] to track
 * @param configuration  - [ComScoreConfiguration] associated with the source to load
 * @param metadata       - [ComScoreMetadata] associated with the source to load
 */
class ComScoreStreamingAnalytics(bitmovinPlayer: BitmovinPlayer, configuration: ComScoreConfiguration, metadata: ComScoreMetadata) {

    private val adapter: ComScoreBitmovinAdapter = ComScoreBitmovinAdapter(bitmovinPlayer, configuration, metadata)

    /**
     * Update metadata for tracked source. This should be called when changing sources.
     *
     * @param metadata - The new [ComScoreMetadata]
     */
    fun updateMetadata(metadata: ComScoreMetadata) {
        adapter.metadata = metadata
    }

    /**
     * Set user consent to [ComScoreUserConsent.GRANTED]
     *
     */
    @Deprecated("Deprecated as of release 1.3.0", replaceWith = ReplaceWith("setPersistentLabel(\"label\" to \"value\")"))
    fun userConsentGranted() {
        adapter.userConsent = ComScoreUserConsent.GRANTED
    }

    /**
     * Set user consent to [ComScoreUserConsent.DENIED]
     *
     */
    @Deprecated("Deprecated as of release 1.3.0", replaceWith = ReplaceWith("setPersistentLabel(\"label\" to \"value\")"))
    fun userConsentDenied() {
        adapter.userConsent = ComScoreUserConsent.DENIED
    }

    /**
     * Set a persistent label on the ComScore [PublisherConfiguration]
     *
     * @param label - the label to set
     */
    fun setPersistentLabel(label: Pair<String, String>) = adapter.setPersistentLabel(label)

    /**
     * Set persistent labels on the ComScore [PublisherConfiguration]
     *
     * @param labels - the labels to set
     */
    fun setPersistentLabels(labels: List<Pair<String, String>>) = adapter.setPersistentLabels(labels)
}
