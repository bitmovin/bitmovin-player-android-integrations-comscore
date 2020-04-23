package com.bitmovin.player.integration.comscore

import com.bitmovin.player.BitmovinPlayer

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
    @Deprecated("Deprecated as of release 1.3.0", replaceWith = ReplaceWith("applyPersistentLabel(\"label\" to \"value\")"))
    fun userConsentGranted() {
        adapter.userConsent = ComScoreUserConsent.GRANTED
    }

    /**
     * Set user consent to [ComScoreUserConsent.DENIED]
     *
     */
    @Deprecated("Deprecated as of release 1.3.0", replaceWith = ReplaceWith("applyPersistentLabel(\"label\" to \"value\")"))
    fun userConsentDenied() {
        adapter.userConsent = ComScoreUserConsent.DENIED
    }

    /**
     * Apply ComScore persistent label
     *
     * @param label - the label to apply
     */
    fun applyPersistentLabel(label: Pair<String, String>) = adapter.applyPersistentLabel(label)

    /**
     * Apply ComScore persistent label
     *
     * @param label - the label to apply
     */
    fun applyPersistentLabels(labels: List<Pair<String, String>>) = adapter.applyPersistentLabels(labels)
}
