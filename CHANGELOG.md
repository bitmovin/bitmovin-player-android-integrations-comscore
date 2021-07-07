# Change Log
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).

## [Unreleased]
### Changed
- Updated ComScore SDK to use MavenCentral

## [1.5.1]
### Changed
- Updated Bitmovin Maven link to new Bintray URL

## [1.5.0]

### Added
- `childDirectedAppMode` to `ComScoreConfiguration` to support ChildDirectedApplicationMode

### Changed
- Updated to ComScore SDK to `6.5.0`

## [1.4.0]

### Changed
- Migrated to ComScore SDK to `6.2.3`

### Removed
- `userConsentGranted()` from `ComScoreAnalytics` and `ComScoreStreamingAnalytics`
- `userConsentDenied()` from `ComScoreAnalytics` and `ComScoreStreamingAnalytics`

## [1.3.1]

### Changed
- `setPersistentLabel()` in  `ComScoreAnalytics` and `ComScoreStreamingAnalytics` to take two strings as params instead of one tuple of strings

## [1.3.0]

### Added
-  `setPersistentLabel()` in  `ComScoreAnalytics` and `ComScoreStreamingAnalytics`

## [1.2.2]

### Changed
- All classes to Kotlin from Java

### Added
- BitLog for custom logging

## [1.2.1]

### Added
- `secureTransmission` flag to `ComScoreConfiguration` to enable/disable https 
