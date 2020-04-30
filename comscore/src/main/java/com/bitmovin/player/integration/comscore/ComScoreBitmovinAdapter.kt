package com.bitmovin.player.integration.comscore

import com.bitmovin.player.BitmovinPlayer
import com.bitmovin.player.api.event.listener.*
import com.bitmovin.player.integration.comscore.util.contentType
import com.bitmovin.player.integration.comscore.util.notifyHiddenEvent
import com.bitmovin.player.integration.comscore.util.notifyHiddenEvents
import com.comscore.streaming.AdType
import com.comscore.streaming.ReducedRequirementsStreamingAnalytics
import kotlin.properties.Delegates

class ComScoreBitmovinAdapter(private val bitmovinPlayer: BitmovinPlayer, private val configuration: ComScoreConfiguration, comScoreMetadata: ComScoreMetadata) {
    companion object {
        private const val ASSET_DURATION_KEY = "ns_st_cl"
    }

    private enum class ComScoreState {
        ADVERTISEMENT,
        STOPPED,
        VIDEO
    }

    private var metadataMap = comScoreMetadata.toMap().toMutableMap()
    private val streamingAnalytics = ReducedRequirementsStreamingAnalytics()
    private var comScoreState = ComScoreState.STOPPED
    private var currentAdDuration = 0.0
    private var currentAdOffset = 0.0

    var metadata: ComScoreMetadata by Delegates.observable(comScoreMetadata) { _, _, newMetadata ->
        metadataMap.clear()
        metadataMap.putAll(newMetadata.toMap())
    }

    @Deprecated("Deprecated as of release 1.3.0", replaceWith = ReplaceWith("setPersistentLabel(\"label\", \"value\")"))
    var userConsent: ComScoreUserConsent by Delegates.observable(configuration.userConsent) { _, _, newUserConsent ->
        configuration.userConsent = newUserConsent
        setPersistentLabel("cs_ucfr", newUserConsent.value)
    }

    init {
        BitLog.isEnabled = configuration.isDebug
        BitLog.d("Version ${BuildConfig.VERSION_NAME}")
        addEventListeners()
    }

    private fun addEventListeners() = with(bitmovinPlayer) {
        addEventListener(OnPlaybackFinishedListener {
            stop()
        })

        addEventListener(OnPlayingListener {
            if (bitmovinPlayer.isAd) {
                transitionToAd(currentAdDuration, currentAdOffset)
            } else {
                transitionToVideoPlay()
            }
        })

        addEventListener(OnPausedListener {
            if (!bitmovinPlayer.isAd) {
                stop()
            }
        })

        addEventListener(OnPausedListener {
            if (!bitmovinPlayer.isAd) {
                stop()
            }
        })

        addEventListener(OnErrorListener {
            stop()
        })

        addEventListener(OnSourceUnloadedListener {
            stop()
        })

        addEventListener(OnAdStartedListener {
            currentAdDuration = it.duration
            currentAdOffset = it.timeOffset
            transitionToAd(currentAdDuration, currentAdOffset)
        })

        addEventListener(OnAdFinishedListener {
            transitionToVideoPlay()
        })
    }

    @Synchronized
    private fun stop() {
        if (comScoreState != ComScoreState.STOPPED) {
            BitLog.d("Stopping ComScore tracking")
            comScoreState = ComScoreState.STOPPED
            streamingAnalytics.stop()
        }
    }

    @Synchronized
    private fun transitionToVideoPlay() {
        var duration = bitmovinPlayer.duration
        if (duration.isInfinite()) {
            duration = 0.0
        } else {
            duration *= 1000
        }
        metadataMap[ASSET_DURATION_KEY] = duration.toInt().toString()
        if (comScoreState != ComScoreState.VIDEO) {
            stop()
            comScoreState = ComScoreState.VIDEO
            BitLog.d("Starting ComScore video content tracking")
            streamingAnalytics.playVideoContentPart(metadataMap, metadata.mediaType.contentType())
        }
    }

    @Synchronized
    private fun transitionToAd(duration: Double, offset: Double) {
        if (comScoreState != ComScoreState.ADVERTISEMENT) {
            stop()
            comScoreState = ComScoreState.ADVERTISEMENT
            val adType = when {
                bitmovinPlayer.isLive -> AdType.LINEAR_LIVE
                offset == 0.0 -> AdType.LINEAR_ON_DEMAND_PRE_ROLL
                offset + duration == bitmovinPlayer.duration -> AdType.LINEAR_ON_DEMAND_POST_ROLL
                else -> AdType.LINEAR_ON_DEMAND_MID_ROLL
            }
            val durationValue = (duration * 1000).toInt().toString()
            val adMap = mapOf(ASSET_DURATION_KEY to durationValue)
            BitLog.d("Starting ComScore ad play tracking")
            streamingAnalytics.playVideoAdvertisement(adMap, adType)
        }
    }

    fun setPersistentLabel(label: String, value: String) {
        notifyHiddenEvent(configuration.publisherId, label, value)
        BitLog.d("ComScore persistent label set: [$label:$value]")
    }

    fun setPersistentLabels(labels: Map<String, String>)  {
        notifyHiddenEvents(configuration.publisherId, labels)
        BitLog.d("ComScore persistent labels set: ${labels.map { "${it.key}:${it.value}" }}")
    }
}
