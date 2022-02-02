package com.bitmovin.player.integration.comscore

import com.bitmovin.player.BitmovinPlayer
import com.bitmovin.player.api.event.listener.*
import com.bitmovin.player.integration.comscore.util.contentType
import com.bitmovin.player.integration.comscore.util.notifyHiddenEvent
import com.bitmovin.player.integration.comscore.util.notifyHiddenEvents
import com.comscore.streaming.AdvertisementMetadata
import com.comscore.streaming.AdvertisementType
import com.comscore.streaming.ContentMetadata
import com.comscore.streaming.StreamingAnalytics
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
    private val streamingAnalytics = StreamingAnalytics()
    private var comScoreState = ComScoreState.STOPPED
    private var currentAdDuration = 0.0
    private var currentAdOffset = 0.0

    var suppressAdAnalytics = false

    var metadata: ComScoreMetadata by Delegates.observable(comScoreMetadata) { _, _, newMetadata ->
        metadataMap.clear()
        metadataMap.putAll(newMetadata.toMap())
    }

    init {
        BitLog.isEnabled = configuration.debug
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
            streamingAnalytics.notifyPause()
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

            val contentMetadata = ContentMetadata.Builder().apply {
                mediaType(metadata.mediaType.contentType())
                customLabels(metadataMap)
            }.build()

            with(streamingAnalytics) {
                setMetadata(contentMetadata)
                notifyPlay()
            }

            BitLog.d("Starting ComScore video content tracking")
        }
    }

    @Synchronized
    private fun transitionToAd(duration: Double, offset: Double) {
        if (comScoreState != ComScoreState.ADVERTISEMENT) {
            stop()

            if (suppressAdAnalytics) {
                BitLog.d("Not tracking ad content as ad analytics is suppressed")
                return
            }

            comScoreState = ComScoreState.ADVERTISEMENT
            val adType = when {
                bitmovinPlayer.isLive -> AdvertisementType.LIVE
                offset == 0.0 -> AdvertisementType.ON_DEMAND_PRE_ROLL
                offset + duration == bitmovinPlayer.duration -> AdvertisementType.ON_DEMAND_POST_ROLL
                else -> AdvertisementType.ON_DEMAND_MID_ROLL
            }
            val durationValue = (duration * 1000).toInt().toString()
            val adMap = mapOf(ASSET_DURATION_KEY to durationValue)

            val adMetadata = AdvertisementMetadata.Builder().apply {
                mediaType(adType)
                customLabels(adMap)
            }.build()

            with(streamingAnalytics) {
                setMetadata(adMetadata)
                notifyPlay()
            }

            BitLog.d("Starting ComScore ad play tracking")
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
