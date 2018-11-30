# BitmovinComScoreAnalytics

# Getting started
## Gradle

Add this to your top level `build.gradle`

```
allprojects {
    repositories {
		maven {
			url  'http://bitmovin.bintray.com/maven'
		}
	}
}
```

And this line to your main project `build.gradle`

```
dependencies {
    compile 'com.bitmovin.player.integrations:comscoreanalytics:0.1.0'
}
```

## Examples

#### Basic setup

How to setup ComScoreAnalytics for app lifecycle tracking. Do this once your application loads

```java
ComScoreConfiguration comScoreConfiguration = new ComScoreConfiguration("YOUR_PUBLISHER_ID", "YOUR_PUBLISHER_SECRET", "YOUR APPLICATION NAME");
ComScoreAnalytics.addConfiguration(comScoreConfiguration);
ComScoreAnalytics.start(getApplicationContext());
```

Once you have created a video player, you can track it via the ComScoreStreamingAnalytics object 

```java

//Create metadata using the builder
ComScoreMetadata comScoreMetadata = new ComScoreMetadataBuilder().setMediaType(ComScoreMediaType.LONG_FORM_ON_DEMAND).setPublisherBrandName("ABC").setProgramTitle("Modern Family").setEpisodeTitle("Rash Decisions").setEpisodeSeasonNumber("1").setEpisodeNumber("2").setContentGenre("Comedy").setStationTitle("Hulu").setCompleteEpisode(true).build();

//Create ComScoreStreamingAnalytics 
comScoreStreamingAnalytics = new ComScoreStreamingAnalytics(bitmovinPlayer, comScoreMetadata);

// Load a source
bitmovinPlayer.load(source)

// Unload a source in order to play and track something else
bitmovinPlayer.unload()

// Update metadata for your new source 
comScoreStreamignAnalytics.update(newMetadata)

//Load your new source 
bitmovinPlayer.load()
```

## Author

Cory Zachman, cory.zachman@bitmovin.com

## License

BitmovinComscoreAnalytics is available under the MIT license. See the LICENSE file for more info.
