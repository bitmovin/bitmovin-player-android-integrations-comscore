package com.bitmovin.player.integration.comscore

import com.bitmovin.player.BitmovinPlayer

/**
 * ComScoreStreaming analytics measures video playback and reports back to ComScore.
 *
 * @param bitmovinPlayer - the video player you want to track
 * @param metadata       - ComScoreMetadata associated with the source you are going to load
 */
class ComScoreStreamingAnalytics(bitmovinPlayer: BitmovinPlayer, metadata: ComScoreMetadata) {

    private val adapter: ComScoreBitmovinAdapter = ComScoreBitmovinAdapter(bitmovinPlayer, metadata)

    /**
     * Updates the metdata for the source you are tracking. You should use method when changing sources. Unload the old source, update the metadata, and load your new source
     *
     * @param metadata
     */
    fun updateMetadata(metadata: ComScoreMetadata) {
        adapter.metadata = metadata
    }
}
