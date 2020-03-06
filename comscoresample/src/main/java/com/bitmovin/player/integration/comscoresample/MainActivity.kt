package com.bitmovin.player.integration.comscoresample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.bitmovin.player.BitmovinPlayer
import com.bitmovin.player.config.PlayerConfiguration
import com.bitmovin.player.config.advertising.AdItem
import com.bitmovin.player.config.advertising.AdSource
import com.bitmovin.player.config.advertising.AdSourceType
import com.bitmovin.player.config.advertising.AdvertisingConfiguration
import com.bitmovin.player.config.media.HLSSource
import com.bitmovin.player.config.media.SourceConfiguration
import com.bitmovin.player.config.media.SourceItem
import com.bitmovin.player.integration.comscore.ComScoreAnalytics
import com.bitmovin.player.integration.comscore.ComScoreConfiguration
import com.bitmovin.player.integration.comscore.ComScoreMediaType
import com.bitmovin.player.integration.comscore.ComScoreMetadata
import com.bitmovin.player.integration.comscore.ComScoreStreamingAnalytics
import com.bitmovin.player.integration.comscore.ComScoreUserConsent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var player: BitmovinPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startAppTracking()
        initPlayer()
        setUIListeners()

        val streamingAnalytics = initStreamingAnalytics()
    }

    private fun setUIListeners() {
        vod_button.setOnClickListener {
            loadVod()
        }
        unload_button.setOnClickListener {
            player.unload()
        }
    }

    private fun startAppTracking() {
        val comScoreConfiguration = ComScoreConfiguration(
            publisherId = "publisherId",
            publisherSecret = "publisherSecret",
            applicationName = "applicationName",
            userConsent = ComScoreUserConsent.GRANTED
        )
        ComScoreAnalytics.start(comScoreConfiguration, applicationContext)
    }

    private fun initStreamingAnalytics(): ComScoreStreamingAnalytics {
        val comScoreMetadata = ComScoreMetadata(
            mediaType = ComScoreMediaType.LONG_FORM_ON_DEMAND,
            publisherBrandName = "ABC",
            programTitle = "Modern Family",
            episodeTitle = "Rash Decisions",
            episodeSeasonNumber = "1",
            episodeNumber = "2",
            contentGenre = "Comedy",
            stationTitle = "Hulu",
            completeEpisode = true
        )
        return ComScoreAnalytics.createComScoreStreamingAnalytics(player, comScoreMetadata)
    }

    private fun initPlayer() {
        // Create AdSources
        val firstAdSource = AdSource(AdSourceType.IMA, IMATags.AD_SOURCE_1)
        val secondAdSource = AdSource(AdSourceType.IMA, IMATags.AD_SOURCE_2)
        val thirdAdSource = AdSource(AdSourceType.IMA, IMATags.AD_SOURCE_3)
        val fourthAdSource = AdSource(AdSourceType.IMA, IMATags.AD_SOURCE_4)

        // Setup a pre-roll ad
        val preRoll = AdItem("pre", thirdAdSource)
        // Setup a mid-roll waterfalling ad at 10% of the content duration
        val midRoll = AdItem("10%", firstAdSource, secondAdSource)
        // Setup a post-roll ad
        val postRoll = AdItem("post", fourthAdSource)

        // Create a source configuration
        val sourceItem = SourceItem(HLSSource("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"))
        val sourceConfig = SourceConfiguration().apply { addSourceItem(sourceItem) }

        // Creating a new PlayerConfiguration
        val playerConfiguration = PlayerConfiguration().apply {
            playbackConfiguration.isAutoplayEnabled = true
            advertisingConfiguration = AdvertisingConfiguration(preRoll, midRoll, postRoll)
            sourceConfiguration = sourceConfig
        }

        player = BitmovinPlayer(applicationContext, playerConfiguration)
        player_view.player = player
    }

    override fun onResume() {
        super.onResume()
        player_view.onResume()
    }

    override fun onPause() {
        super.onPause()
        player_view.onPause()
    }

    private fun loadVod() {
        val sourceItem = SourceItem(HLSSource("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"))
        val sourceConfig = SourceConfiguration().apply { addSourceItem(sourceItem) }
        player.load(sourceConfig)
    }
}
