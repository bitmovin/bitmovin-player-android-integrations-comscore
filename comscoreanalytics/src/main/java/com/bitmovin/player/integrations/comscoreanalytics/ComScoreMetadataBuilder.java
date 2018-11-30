package com.bitmovin.player.integrations.comscoreanalytics;

public class ComScoreMetadataBuilder {
    private ComScoreMediaType mediaType;
    private String uniqueContentId;
    private String publisherBrandName;
    private String programTitle;
    private String programId;
    private String episodeTitle;
    private String episodeId;
    private String episodeSeasonNumber;
    private String episodeNumber;
    private String contentGenre;
    boolean advertisementLoad = false;
    private String digitalAirdate;
    private String tvAirdate;
    private String stationTitle;
    private String c3;
    private String c4;
    private String c6;
    boolean completeEpisode = false;
    private String feedType;

    public ComScoreMetadataBuilder() {

    }

    public ComScoreMetadataBuilder setMediaType(ComScoreMediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public ComScoreMetadataBuilder setUniqueContentId(String uniqueContentId) {
        this.uniqueContentId = uniqueContentId;
        return this;
    }

    public ComScoreMetadataBuilder setPublisherBrandName(String publisherBrandName) {
        this.publisherBrandName = publisherBrandName;
        return this;
    }

    public ComScoreMetadataBuilder setProgramTitle(String programTitle) {
        this.programTitle = programTitle;
        return this;
    }

    public ComScoreMetadataBuilder setProgramId(String programId) {
        this.programId = programId;
        return this;
    }

    public ComScoreMetadataBuilder setEpisodeTitle(String episodeTitle) {
        this.episodeTitle = episodeTitle;
        return this;
    }

    public ComScoreMetadataBuilder setEpisodeId(String episodeId) {
        this.episodeId = episodeId;
        return this;
    }

    public ComScoreMetadataBuilder setEpisodeSeasonNumber(String episodeSeasonNumber) {
        this.episodeSeasonNumber = episodeSeasonNumber;
        return this;
    }

    public ComScoreMetadataBuilder setEpisodeNumber(String episodeNumber) {
        this.episodeNumber = episodeNumber;
        return this;
    }

    public ComScoreMetadataBuilder setContentGenre(String contentGenre) {
        this.contentGenre = contentGenre;
        return this;
    }

    public ComScoreMetadataBuilder setAdvertisementLoad(boolean advertisementLoad) {
        this.advertisementLoad = advertisementLoad;
        return this;
    }

    public ComScoreMetadataBuilder setDigitalAirdate(String digitalAirdate) {
        this.digitalAirdate = digitalAirdate;
        return this;
    }

    public ComScoreMetadataBuilder setTvAirdate(String tvAirdate) {
        this.tvAirdate = tvAirdate;
        return this;
    }

    public ComScoreMetadataBuilder setStationTitle(String stationTitle) {
        this.stationTitle = stationTitle;
        return this;
    }

    public ComScoreMetadataBuilder setC3(String c3) {
        this.c3 = c3;
        return this;
    }

    public ComScoreMetadataBuilder setC4(String c4) {
        this.c4 = c4;
        return this;
    }

    public ComScoreMetadataBuilder setC6(String c6) {
        this.c6 = c6;
        return this;
    }

    public ComScoreMetadataBuilder setCompleteEpisode(boolean completeEpisode) {
        this.completeEpisode = completeEpisode;
        return this;
    }

    public ComScoreMetadataBuilder setFeedType(String feedType) {
        this.feedType = feedType;
        return this;
    }

    public ComScoreMetadata build(){
        return new ComScoreMetadata(mediaType,uniqueContentId,publisherBrandName,programTitle,programId,episodeTitle,episodeId,episodeSeasonNumber,episodeNumber,contentGenre,advertisementLoad,digitalAirdate,tvAirdate,stationTitle,c3,c4,c6,completeEpisode,feedType);
    }

}
