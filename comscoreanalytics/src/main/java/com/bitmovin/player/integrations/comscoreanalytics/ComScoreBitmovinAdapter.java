package com.bitmovin.player.integrations.comscoreanalytics;

import android.util.Log;

import com.bitmovin.player.BitmovinPlayer;
import com.bitmovin.player.api.event.data.AdFinishedEvent;
import com.bitmovin.player.api.event.data.AdStartedEvent;
import com.bitmovin.player.api.event.data.ErrorEvent;
import com.bitmovin.player.api.event.data.PausedEvent;
import com.bitmovin.player.api.event.data.PlaybackFinishedEvent;
import com.bitmovin.player.api.event.data.PlayingEvent;
import com.bitmovin.player.api.event.data.SourceUnloadedEvent;
import com.bitmovin.player.api.event.listener.OnAdFinishedListener;
import com.bitmovin.player.api.event.listener.OnAdStartedListener;
import com.bitmovin.player.api.event.listener.OnErrorListener;
import com.bitmovin.player.api.event.listener.OnPausedListener;
import com.bitmovin.player.api.event.listener.OnPlaybackFinishedListener;
import com.bitmovin.player.api.event.listener.OnPlayingListener;
import com.bitmovin.player.api.event.listener.OnSourceUnloadedListener;
import com.comscore.streaming.AdType;
import com.comscore.streaming.ContentType;
import com.comscore.streaming.ReducedRequirementsStreamingAnalytics;

import java.util.HashMap;
import java.util.Map;

public class ComScoreBitmovinAdapter {
    public static final String TAG = "ComScoreBitmovinAdapter";

    private BitmovinPlayer bitmovinPlayer;
    private Map<String, String> metadata;
    private int contentType;
    private ReducedRequirementsStreamingAnalytics streamingAnalytics;
    private ComScoreState comScoreState = ComScoreState.STOPPED;

    public ComScoreBitmovinAdapter(BitmovinPlayer bitmovinPlayer, ComScoreMetadata comScoreMetadata) {
        this.bitmovinPlayer = bitmovinPlayer;
        metadata = comScoreMetadata.toDictionary();
        contentType = comScoreContentType(comScoreMetadata.getMediaType());
        this.streamingAnalytics = new ReducedRequirementsStreamingAnalytics();

        this.bitmovinPlayer.addEventListener(onPlaybackFinishedListener);
        this.bitmovinPlayer.addEventListener(onPlayingListener);
        this.bitmovinPlayer.addEventListener(onPausedListener);
        this.bitmovinPlayer.addEventListener(onErrorListener);
        this.bitmovinPlayer.addEventListener(onSourceUnloadedListener);
        this.bitmovinPlayer.addEventListener(onAdStartedListener);
        this.bitmovinPlayer.addEventListener(onAdFinishedListener);
    }

    public void updateMetadata(ComScoreMetadata metadata) {
        this.metadata = metadata.toDictionary();
        contentType = comScoreContentType(metadata.getMediaType());
    }

    private OnPlaybackFinishedListener onPlaybackFinishedListener = new OnPlaybackFinishedListener() {
        @Override
        public void onPlaybackFinished(PlaybackFinishedEvent playbackFinishedEvent) {
            stop();
        }
    };

    private OnPlayingListener onPlayingListener = new OnPlayingListener() {
        @Override
        public void onPlaying(PlayingEvent playingEvent) {
            transitionToVideoPlay();
        }
    };

    private OnPausedListener onPausedListener = new OnPausedListener() {
        @Override
        public void onPaused(PausedEvent pausedEvent) {
            stop();
        }
    };

    private OnErrorListener onErrorListener = new OnErrorListener() {
        @Override
        public void onError(ErrorEvent errorEvent) {
            stop();
        }
    };

    private OnSourceUnloadedListener onSourceUnloadedListener = new OnSourceUnloadedListener() {
        @Override
        public void onSourceUnloaded(SourceUnloadedEvent sourceUnloadedEvent) {
            stop();
        }
    };

    private OnAdStartedListener onAdStartedListener = new OnAdStartedListener() {
        @Override
        public void onAdStarted(AdStartedEvent adStartedEvent) {
            transitionToAd(bitmovinPlayer.getDuration(), adStartedEvent.getTimeOffset());
        }
    };

    private OnAdFinishedListener onAdFinishedListener = new OnAdFinishedListener() {
        @Override
        public void onAdFinished(AdFinishedEvent adFinishedEvent) {
            transitionToVideoPlay();
        }
    };

    private int comScoreContentType(ComScoreMediaType mediaType) {
        switch (mediaType) {
            case LIVE:
                return ContentType.LIVE;
            case USER_GENERATED_SHORT_FORM_ON_DEMAND:
                return ContentType.USER_GENERATED_SHORT_FORM_ON_DEMAND;
            case USER_GENERATED_LONG_FORM_ON_DEMAND:
                return ContentType.USER_GENERATED_LONG_FORM_ON_DEMAND;
            case USER_GENERATED_LIVE:
                return ContentType.USER_GENERATED_LIVE;
            case BUMPER:
                return ContentType.BUMPER;
            case LONG_FORM_ON_DEMAND:
                return ContentType.LONG_FORM_ON_DEMAND;
            case SHORT_FORM_ON_DEMAND:
                return ContentType.SHORT_FORM_ON_DEMAND;
            default:
                return ContentType.OTHER;
        }
    }

    private void stop() {
        if (comScoreState != ComScoreState.STOPPED) {
            Log.d(TAG, "Stopping ComScore Tracking");
            comScoreState = ComScoreState.STOPPED;
            streamingAnalytics.stop();
        }
    }

    private void transitionToVideoPlay() {
        double duration = bitmovinPlayer.getDuration();
        if (Double.isInfinite(duration)) {
            duration = 0;
        }else {
            duration = duration *1000;
        }
        metadata.put("ns_st_cl",String.valueOf(duration));

        if (comScoreState != ComScoreState.VIDEO) {
            if (comScoreState != ComScoreState.STOPPED) {
                Log.d(TAG, "Stopping ComScore Tracking");
                streamingAnalytics.stop();
            }
            comScoreState = ComScoreState.VIDEO;
            Log.d(TAG, "ComScore Tracking Video Content");
            streamingAnalytics.playVideoContentPart(metadata, contentType);
        }
    }

    private void transitionToAd(double duration, double offset) {
        if (comScoreState != ComScoreState.ADVERTISEMENT) {
            if (comScoreState != ComScoreState.STOPPED) {
                Log.d(TAG, "Stopping ComScore Tracking");
                streamingAnalytics.stop();
            }

            comScoreState = ComScoreState.ADVERTISEMENT;

            int adType;

            if (bitmovinPlayer.isLive()) {
                adType = AdType.LINEAR_LIVE;
            } else {
                if (offset == 0) {
                    adType = AdType.LINEAR_ON_DEMAND_PRE_ROLL;
                } else if (offset + duration == bitmovinPlayer.getDuration()) {
                    adType = AdType.LINEAR_ON_DEMAND_POST_ROLL;
                } else {
                    adType = AdType.LINEAR_ON_DEMAND_MID_ROLL;
                }
            }

            Map<String, String> adMap = new HashMap<>();
            adMap.put("ns_st_cl", String.valueOf(duration * 1000));
            Log.d(TAG, "ComScore Tracking Ad Play");
            streamingAnalytics.playAudioAdvertisement(adMap, adType);
        }
    }

    private enum ComScoreState {
        ADVERTISEMENT,
        STOPPED,
        VIDEO
    }
}