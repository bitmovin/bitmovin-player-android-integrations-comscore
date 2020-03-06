package com.bitmovin.player.integration.comscore

import com.bitmovin.player.BitmovinPlayer

/**
 * ComScoreStreaming analytics measures video playback and reports back to ComScore.
 *
 * @param bitmovinPlayer - the video player you want to track
 * @param configuration  - ComScoreConfiguration associated with the source you are going to load
 * @param metadata       - ComScoreMetadata associated with the source you are going to load
 */
class ComScoreStreamingAnalytics(bitmovinPlayer: BitmovinPlayer, configuration: ComScoreConfiguration, metadata: ComScoreMetadata) {

    private val adapter: ComScoreBitmovinAdapter = ComScoreBitmovinAdapter(bitmovinPlayer, configuration, metadata)

    /**
     * Updates the metdata for the source you are tracking. You should use method when changing sources.
     * Unload the old source, update the metadata, and load your new source
     *
     * @param metadata
     */
    fun updateMetadata(metadata: ComScoreMetadata) {
        adapter.metadata = metadata
    }

    /**
     * Sets the user consent value to granted
     */
    fun userConsentGranted() {
        adapter.userConsent = ComScoreUserConsent.GRANTED
    }

    /**
     * Sets the user consent value to denied
     */
    fun userConsentDenied() {
        adapter.userConsent = ComScoreUserConsent.DENIED
    }
}
