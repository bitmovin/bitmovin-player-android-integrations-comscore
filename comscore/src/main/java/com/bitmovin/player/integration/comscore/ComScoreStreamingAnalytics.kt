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
     * Set a persistent label on the ComScore [PublisherConfiguration]
     *
     * @param label - the name of the label
     * @param value - the value of the label
     */
    fun setPersistentLabel(label: String, value: String) = adapter.setPersistentLabel(label, value)

    /**
     * Set persistent labels on the ComScore [PublisherConfiguration]
     *
     * @param labels - the labels to set
     */
    fun setPersistentLabels(labels: Map<String, String>) = adapter.setPersistentLabels(labels)

    /**
    Enable/disable comscore ad content tracking
    - Parameters:
    - suppress: The enable/disable flag
     */
    fun suppressAdAnalytics(suppress: Boolean) {
        adapter.suppressAdAnalytics = suppress
    }
}
