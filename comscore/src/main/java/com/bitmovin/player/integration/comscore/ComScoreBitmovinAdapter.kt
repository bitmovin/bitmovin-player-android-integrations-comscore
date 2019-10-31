package com.bitmovin.player.integration.comscore

import android.util.Log
import com.bitmovin.player.BitmovinPlayer
import com.bitmovin.player.api.event.listener.*
import com.bitmovin.player.integration.comscore.ComScoreMediaType.*
import com.comscore.streaming.AdType.*
import com.comscore.streaming.ContentType
import com.comscore.streaming.ReducedRequirementsStreamingAnalytics
import java.lang.Double.isInfinite
import kotlin.math.roundToInt
import kotlin.properties.Delegates

class ComScoreBitmovinAdapter(private val bitmovinPlayer: BitmovinPlayer, comScoreMetadata: ComScoreMetadata) {

    private val TAG = "ComScoreBitmovinAdapter"

    private enum class ComScoreState {
        ADVERTISEMENT,
        STOPPED,
        VIDEO
    }

    var metadata: ComScoreMetadata by Delegates.observable(comScoreMetadata) { _, _, newMetadata ->
        metadataMap = newMetadata.toMap()
        contentType = comScoreContentType(newMetadata.mediaType)
    }
    private var metadataMap: MutableMap<String, String?> = comScoreMetadata.toMap()
    private var contentType: Int = comScoreContentType(comScoreMetadata.mediaType)
    private val streamingAnalytics: ReducedRequirementsStreamingAnalytics = ReducedRequirementsStreamingAnalytics()
    private var comScoreState = ComScoreState.STOPPED
    private var currentAdDuration: Double = 0.0
    private var currentAdOffset: Double = 0.0

    private val onPlaybackFinishedListener = OnPlaybackFinishedListener { stop() }

    private val onPlayingListener = OnPlayingListener {
        if (bitmovinPlayer.isAd) {
            transitionToAd(currentAdDuration, currentAdOffset)
        } else {
            transitionToVideoPlay()
        }
    }

    private val onPausedListener = OnPausedListener {
        if (!bitmovinPlayer.isAd) {
            stop()
        }
    }

    private val onErrorListener = OnErrorListener { stop() }

    private val onSourceUnloadedListener = OnSourceUnloadedListener { stop() }

    private val onAdStartedListener = OnAdStartedListener { adStartedEvent ->
        currentAdDuration = adStartedEvent.duration
        currentAdOffset = adStartedEvent.timeOffset
        transitionToAd(currentAdDuration, currentAdOffset)
    }

    private val onAdFinishedListener = OnAdFinishedListener { transitionToVideoPlay() }

    init {
        this.bitmovinPlayer.addEventListener(onPlaybackFinishedListener)
        this.bitmovinPlayer.addEventListener(onPlayingListener)
        this.bitmovinPlayer.addEventListener(onPausedListener)
        this.bitmovinPlayer.addEventListener(onErrorListener)
        this.bitmovinPlayer.addEventListener(onSourceUnloadedListener)
        this.bitmovinPlayer.addEventListener(onAdStartedListener)
        this.bitmovinPlayer.addEventListener(onAdFinishedListener)
    }

    private fun comScoreContentType(mediaType: ComScoreMediaType): Int = when (mediaType) {
        LIVE -> ContentType.LIVE
        USER_GENERATED_SHORT_FORM_ON_DEMAND -> ContentType.USER_GENERATED_SHORT_FORM_ON_DEMAND
        USER_GENERATED_LONG_FORM_ON_DEMAND -> ContentType.USER_GENERATED_LONG_FORM_ON_DEMAND
        USER_GENERATED_LIVE -> ContentType.USER_GENERATED_LIVE
        BUMPER -> ContentType.BUMPER
        LONG_FORM_ON_DEMAND -> ContentType.LONG_FORM_ON_DEMAND
        SHORT_FORM_ON_DEMAND -> ContentType.SHORT_FORM_ON_DEMAND
        else -> ContentType.OTHER
    }

    private fun stop() {
        if (comScoreState != ComScoreState.STOPPED) {
            Log.d(TAG, "Stopping ComScore Tracking")
            comScoreState = ComScoreState.STOPPED
            streamingAnalytics.stop()
        }
    }

    private fun transitionToVideoPlay() {
        val duration = if (isInfinite(bitmovinPlayer.duration)) {
            0.0
        } else {
            bitmovinPlayer.duration * 1000
        }

        metadataMap["ns_st_cl"] = duration.toInt().toString()

        if (comScoreState != ComScoreState.VIDEO) {
            stop()
            comScoreState = ComScoreState.VIDEO
            Log.d(TAG, "ComScore Tracking Video Content")
            streamingAnalytics.playVideoContentPart(metadataMap, contentType)
        }
    }

    private fun transitionToAd(duration: Double, offset: Double) {
        if (comScoreState != ComScoreState.ADVERTISEMENT) {
            stop()
            comScoreState = ComScoreState.ADVERTISEMENT

            val adType = when {
                bitmovinPlayer.isLive -> LINEAR_LIVE
                offset == 0.0 -> LINEAR_ON_DEMAND_PRE_ROLL
                offset + duration == bitmovinPlayer.duration -> LINEAR_ON_DEMAND_POST_ROLL
                else -> LINEAR_ON_DEMAND_MID_ROLL
            }

            val durationValue = (duration * 1000).roundToInt().toString()
            val adMap = mapOf("ns_st_cl" to durationValue)
            Log.d(TAG, "ComScore Tracking Ad Play")
            streamingAnalytics.playVideoAdvertisement(adMap, adType)
        }
    }
}
