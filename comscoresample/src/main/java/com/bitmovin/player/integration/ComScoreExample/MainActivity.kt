package com.bitmovin.player.integration.ComScoreExample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import com.bitmovin.player.BitmovinPlayer
import com.bitmovin.player.config.PlayerConfiguration
import com.bitmovin.player.config.advertising.AdItem
import com.bitmovin.player.config.advertising.AdSource
import com.bitmovin.player.config.advertising.AdSourceType
import com.bitmovin.player.config.advertising.AdvertisingConfiguration
import com.bitmovin.player.config.media.HLSSource
import com.bitmovin.player.config.media.SourceConfiguration
import com.bitmovin.player.config.media.SourceItem
import com.bitmovin.player.integration.comscore.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val AD_SOURCE_1 = "https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/single_ad_samples&ciu_szs=300x250&impl=s&gdfp_req=1&env=vp&output=vast&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ct%3Dredirecterror&nofb=1&correlator="
    private val AD_SOURCE_2 = "https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/single_ad_samples&ciu_szs=300x250&impl=s&gdfp_req=1&env=vp&output=vast&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ct%3Dlinear&correlator="
    private val AD_SOURCE_3 = "https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/single_ad_samples&ciu_szs=300x250&impl=s&gdfp_req=1&env=vp&output=vast&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ct%3Dskippablelinear&correlator="
    private val AD_SOURCE_4 = "https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/single_ad_samples&ciu_szs=300x250&impl=s&gdfp_req=1&env=vp&output=vast&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ct%3Dredirectlinear&correlator="

    private lateinit var bitmovinPlayer: BitmovinPlayer
    private lateinit var comScoreStreamingAnalytics: ComScoreStreamingAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vodButton.setOnClickListener { loadVod() }
        unloadButton.setOnClickListener { unload() }

        val comScoreConfiguration = ComScoreConfiguration("YOUR_PUBLISHER_ID", "YOUR_PUBLISHER_SECRET", "APPLICATION_NAME")
        ComScoreAnalytics.start(comScoreConfiguration, applicationContext)

        // Create ad configuration
        val firstAdSource = AdSource(AdSourceType.IMA, AD_SOURCE_1)
        val secondAdSource = AdSource(AdSourceType.IMA, AD_SOURCE_2)
        val thirdAdSource = AdSource(AdSourceType.IMA, AD_SOURCE_3)
        val fourthAdSource = AdSource(AdSourceType.IMA, AD_SOURCE_4)
        val preRoll = AdItem("pre", thirdAdSource)
        val midRoll = AdItem("10%", firstAdSource, secondAdSource)
        val postRoll = AdItem("post", fourthAdSource)
        val advertisingConfiguration = AdvertisingConfiguration(preRoll, midRoll, postRoll)

        // Create a source configuration
        val sourceItem = SourceItem(HLSSource("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"))
        val sourceConfig = SourceConfiguration()
        sourceConfig.addSourceItem(sourceItem)

        // Creating a new PlayerConfiguration
        val playerConfiguration = PlayerConfiguration()
        playerConfiguration.playbackConfiguration.isAutoplayEnabled = true
        playerConfiguration.advertisingConfiguration = advertisingConfiguration
        playerConfiguration.sourceConfiguration = sourceConfig

        bitmovinPlayer = BitmovinPlayer(applicationContext, playerConfiguration)
        bitmovinPlayerView.player = bitmovinPlayer

        val comScoreMetadata = ComScoreMetadata(
            mediaType = ComScoreMediaType.LONG_FORM_ON_DEMAND,
            publisherBrandName = "ABC",
            programTitle = "Modern Family",
            episodeTitle = "Rash Decisions",
            episodeSeasonNumber = "1",
            episodeNumber = "2",
            contentGenre = "Comdey",
            stationTitle = "Hulu",
            completeEpisode = true
        )
        comScoreStreamingAnalytics = ComScoreAnalytics.createComScoreStreamingAnalytics(bitmovinPlayer, comScoreMetadata)
    }

    override fun onResume() {
        super.onResume()
        bitmovinPlayerView.onResume()
    }

    override fun onPause() {
        super.onPause()
        bitmovinPlayerView.onPause()
    }

    private fun loadVod() {
        val sourceItem = SourceItem(HLSSource("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"))
        val sourceConfig = SourceConfiguration()
        sourceConfig.addSourceItem(sourceItem)

        bitmovinPlayer.load(sourceConfig)
    }

    private fun unload() {
        bitmovinPlayer.unload()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        var handled = false

        when (keyCode) {
            KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_BUTTON_A ->
                // ... handle selections
                handled = true
            KeyEvent.KEYCODE_DPAD_LEFT ->
                // ... handle left action
                handled = true
            KeyEvent.KEYCODE_DPAD_RIGHT ->
                // ... handle right action
                handled = true
        }

        return super.onKeyDown(keyCode, event)
    }
}
