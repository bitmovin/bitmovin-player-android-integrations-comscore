package com.bitmovin.player.integrations.comscoreanalytics;

import com.bitmovin.player.BitmovinPlayer;

public class ComScoreStreamingAnalytics {
    ComScoreBitmovinAdapter adapter;

    public ComScoreStreamingAnalytics(BitmovinPlayer bitmovinPlayer, ComScoreMetadata metadata) {
        adapter = new ComScoreBitmovinAdapter(bitmovinPlayer, metadata);
    }

    public void updateMetadata(ComScoreMetadata metadata) {
        adapter.updateMetadata(metadata);
    }
}
