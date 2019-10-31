package com.bitmovin.player.integration.ComScoreExample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.bitmovin.player.BitmovinPlayer;
import com.bitmovin.player.BitmovinPlayerView;
import com.bitmovin.player.config.PlayerConfiguration;
import com.bitmovin.player.config.StyleConfiguration;
import com.bitmovin.player.config.advertising.AdItem;
import com.bitmovin.player.config.advertising.AdSource;
import com.bitmovin.player.config.advertising.AdSourceType;
import com.bitmovin.player.config.advertising.AdvertisingConfiguration;
import com.bitmovin.player.config.media.HLSSource;
import com.bitmovin.player.config.media.SourceConfiguration;
import com.bitmovin.player.config.media.SourceItem;
import com.bitmovin.player.integration.comscore.ComScoreAnalytics;
import com.bitmovin.player.integration.comscore.ComScoreConfiguration;
import com.bitmovin.player.integration.comscore.ComScoreMediaType;
import com.bitmovin.player.integration.comscore.ComScoreMetadata;
import com.bitmovin.player.integration.comscore.ComScoreStreamingAnalytics;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, KeyEvent.Callback {
    // These are IMA Sample Tags from https://developers.google.com/interactive-media-ads/docs/sdks/android/tags
    private static final String AD_SOURCE_1 = "https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/single_ad_samples&ciu_szs=300x250&impl=s&gdfp_req=1&env=vp&output=vast&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ct%3Dredirecterror&nofb=1&correlator=";
    private static final String AD_SOURCE_2 = "https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/single_ad_samples&ciu_szs=300x250&impl=s&gdfp_req=1&env=vp&output=vast&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ct%3Dlinear&correlator=";
    private static final String AD_SOURCE_3 = "https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/single_ad_samples&ciu_szs=300x250&impl=s&gdfp_req=1&env=vp&output=vast&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ct%3Dskippablelinear&correlator=";
    private static final String AD_SOURCE_4 = "https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/single_ad_samples&ciu_szs=300x250&impl=s&gdfp_req=1&env=vp&output=vast&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ct%3Dredirectlinear&correlator=";

    private BitmovinPlayerView bitmovinPlayerView;
    private BitmovinPlayer bitmovinPlayer;
    private Button vodButton;
    private Button unloadButton;
    private Handler handler = new Handler(Looper.getMainLooper());
    private ComScoreStreamingAnalytics comScoreStreamingAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vodButton = findViewById(R.id.vod_button);
        unloadButton = findViewById(R.id.unload_button);
        bitmovinPlayerView = findViewById(R.id.bitmovinPlayerView);
        vodButton.setOnClickListener(this);

        ComScoreConfiguration comScoreConfiguration = new ComScoreConfiguration("YOUR_PUBLISHER_ID", "YOUR_PUBLISHER_SECRET", "APPLICATION_NAME");
        ComScoreAnalytics.INSTANCE.start(comScoreConfiguration, getApplicationContext());

        // Create new StyleConfiguration
        StyleConfiguration styleConfiguration = new StyleConfiguration();

        // Create AdSources
        AdSource firstAdSource = new AdSource(AdSourceType.IMA, AD_SOURCE_1);
        AdSource secondAdSource = new AdSource(AdSourceType.IMA, AD_SOURCE_2);
        AdSource thirdAdSource = new AdSource(AdSourceType.IMA, AD_SOURCE_3);
        AdSource fourthAdSource = new AdSource(AdSourceType.IMA, AD_SOURCE_4);

        // Setup a pre-roll ad
        AdItem preRoll = new AdItem("pre", thirdAdSource);
        // Setup a mid-roll waterfalling ad at 10% of the content duration
        // NOTE: AdItems containing more than one AdSource, will be executed as waterfalling ad
        AdItem midRoll = new AdItem("10%", firstAdSource, secondAdSource);
        // Setup a post-roll ad
        AdItem postRoll = new AdItem("post", fourthAdSource);
        AdvertisingConfiguration advertisingConfiguration = new AdvertisingConfiguration(preRoll, midRoll, postRoll);

        // Create a source configuration
        SourceItem sourceItem = new SourceItem(new HLSSource("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"));
        SourceConfiguration sourceConfig = new SourceConfiguration();
        sourceConfig.addSourceItem(sourceItem);

        // Creating a new PlayerConfiguration
        PlayerConfiguration playerConfiguration = new PlayerConfiguration();
        playerConfiguration.getPlaybackConfiguration().setAutoplayEnabled(true);
        playerConfiguration.setStyleConfiguration(styleConfiguration);
        playerConfiguration.setAdvertisingConfiguration(advertisingConfiguration);
        playerConfiguration.setSourceConfiguration(sourceConfig);

        bitmovinPlayer = new BitmovinPlayer(getApplicationContext(), playerConfiguration);
        bitmovinPlayerView.setPlayer(bitmovinPlayer);

//        ComScoreMetadata comScoreMetadata = new ComScoreMetadata(ComScoreMediaType.LONG_FORM_ON_DEMAND);
//        comScoreMetadata.setPublisherBrandName("ABC");
//        comScoreMetadata.setProgramTitle("Modern Family");
//        comScoreMetadata.setEpisodeTitle("Rash Decisions");
//        comScoreMetadata.setEpisodeSeasonNumber("1");
//        comScoreMetadata.setEpisodeNumber("2");
//        comScoreMetadata.setContentGenre("Comedy");
//        comScoreMetadata.setStationTitle("Hulu");
//        comScoreMetadata.setCompleteEpisode(true);
//        comScoreStreamingAnalytics = ComScoreAnalytics.INSTANCE.createComScoreStreamingAnalytics(bitmovinPlayer, comScoreMetadata);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (bitmovinPlayerView != null) {
            this.bitmovinPlayerView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (bitmovinPlayerView != null) {
            this.bitmovinPlayerView.onPause();
        }
    }

    private void loadLive() {
        SourceItem sourceItem = new SourceItem(new HLSSource("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"));
        SourceConfiguration sourceConfig = new SourceConfiguration();
        sourceConfig.addSourceItem(sourceItem);

        bitmovinPlayer.load(sourceConfig);
    }

    private void loadVod() {
        SourceItem sourceItem = new SourceItem(new HLSSource("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"));
        SourceConfiguration sourceConfig = new SourceConfiguration();
        sourceConfig.addSourceItem(sourceItem);

        bitmovinPlayer.load(sourceConfig);
    }

    private void unload() {
        bitmovinPlayer.unload();
    }


    @Override
    public void onClick(View v) {
        if (v == vodButton) {
            loadVod();
        } else if (v == unloadButton) {
            unload();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean handled = false;

        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_BUTTON_A:
                // ... handle selections
                handled = true;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                // ... handle left action
                handled = true;
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                // ... handle right action
                handled = true;
                break;
        }

        return super.onKeyDown(keyCode, event);
    }
}
