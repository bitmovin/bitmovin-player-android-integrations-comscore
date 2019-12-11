package com.bitmovin.player.integration.comscore;

import com.bitmovin.player.BitmovinPlayer;

public class ComScoreStreamingAnalytics {
    private ComScoreBitmovinAdapter adapter;

    /**
     * ComScoreStreaming analytics measures video playback and reports back to ComScore.
     *
     * @param bitmovinPlayer - the video player you want to track
     * @param configuration  - ComScoreConfiguration associated with the source you are going to load
     * @param metadata       - ComScoreMetadata associated with the source you are going to load
     */
    ComScoreStreamingAnalytics(BitmovinPlayer bitmovinPlayer, ComScoreConfiguration configuration, ComScoreMetadata metadata) {
        adapter = new ComScoreBitmovinAdapter(bitmovinPlayer, configuration, metadata);
    }

    /**
     * Updates the metdata for the source you are tracking. You should use method when changing sources. Unload the old source, update the metadata, and load your new source
     *
     * @param metadata
     */
    public void updateMetadata(ComScoreMetadata metadata) {
        adapter.updateMetadata(metadata);
    }

    /**
     * Sets the user consent value to granted
     */
    public void userConsentGranted() {
        adapter.userConsentGranted();
    }

    /**
     * Sets the user consent value to denied
     */
    public void userConsentDenied() {
        adapter.userConsentDenied();
    }
}
