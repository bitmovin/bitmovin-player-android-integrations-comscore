# BitmovinComScoreAnalytics

# Getting started
## Gradle

Add this to your top level `build.gradle`

```
allprojects {
    repositories {
        maven {
            url  'https://bitmovin.jfrog.io/artifactory/public-releases'
        }
    }
}
```

And this line to your main project `build.gradle`

```
dependencies {
    implementation 'com.bitmovin.player.integrations:comscoreanalytics:1.2.2'
}
```

## Examples

#### Basic setup

Start ComScoreAnalytics to begin app lifecycle tracking

```kotlin
val comScoreConfiguration = ComScoreConfiguration(
    publisherId = "publisherId",
    publisherSecret = "publisherSecret",
    applicationName = "applicationName"
)
ComScoreAnalytics.start(comScoreConfiguration, applicationContext)
```

Track player analytics with ComScoreStreamingAnalytics

```kotlin
// Create metadata using the builder
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

// Create ComScoreStreamingAnalytics 
val streamingAnalytics = initStreamingAnalytics()

// Load a source
player.load(source)
```

Switching sources

```kotlin
// Unload old source
player.unload()

// Update metadata for new source 
streamingAnalytics.update(newMetadata)

// Load new source 
player.load()
```

## Author

Cory Zachman, cory.zachman@bitmovin.com

## License

BitmovinComscoreAnalytics is available under the MIT license. See the LICENSE file for more info.
