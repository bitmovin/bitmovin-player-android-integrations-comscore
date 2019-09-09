package com.bitmovin.player.integration.comscore;

import org.apache.commons.lang3.Validate;

import java.util.HashMap;
import java.util.Map;

public class ComScoreMetadata {
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
    private boolean advertisementLoad = false;
    private String digitalAirdate;
    private String tvAirdate;
    private String stationTitle;
    private String c3;
    private String c4;
    private String c6;
    private boolean completeEpisode = false;
    private String feedType;

    private ComScoreMetadata(ComScoreMediaType mediaType, String uniqueContentId, String publisherBrandName, String programTitle, String programId, String episodeTitle, String episodeId, String episodeSeasonNumber, String episodeNumber, String contentGenre, boolean advertisementLoad, String digitalAirdate, String tvAirdate, String stationTitle, String c3, String c4, String c6, boolean completeEpisode, String feedType) {
        Validate.notNull(mediaType);

        this.mediaType = mediaType;
        this.uniqueContentId = uniqueContentId;
        this.publisherBrandName = publisherBrandName;
        this.programTitle = programTitle;
        this.programId = programId;
        this.episodeTitle = episodeTitle;
        this.episodeId = episodeId;
        this.episodeSeasonNumber = episodeSeasonNumber;
        this.episodeNumber = episodeNumber;
        this.contentGenre = contentGenre;
        this.advertisementLoad = advertisementLoad;
        this.digitalAirdate = digitalAirdate;
        this.tvAirdate = tvAirdate;
        this.stationTitle = stationTitle;
        this.c3 = c3;
        this.c4 = c4;
        this.c6 = c6;
        this.completeEpisode = completeEpisode;
        this.feedType = feedType;
    }

    public Map<String, String> toDictionary() {
        Map<String, String> dictionary = new HashMap<>();
        dictionary.put("ns_st_ci", uniqueContentId);
        dictionary.put("ns_st_pu", publisherBrandName);
        dictionary.put("ns_st_pr", programTitle);
        dictionary.put("ns_st_tpr", programId);
        dictionary.put("ns_st_ep", episodeTitle);
        dictionary.put("ns_st_tep", episodeId);
        dictionary.put("ns_st_sn", episodeSeasonNumber);
        dictionary.put("ns_st_en", episodeNumber);
        dictionary.put("ns_st_ge", contentGenre);

        if (advertisementLoad) {
            dictionary.put("ns_st_ia", "1");
        }

        dictionary.put("ns_st_ddt", digitalAirdate);
        dictionary.put("ns_st_tdt", tvAirdate);
        dictionary.put("ns_st_st", stationTitle);
        dictionary.put("c3", c3);
        dictionary.put("c4", c4);
        dictionary.put("c6", c6);
        if (completeEpisode) {
            dictionary.put("ns_st_ce", "1");
        }
        dictionary.put("ns_st_ft", feedType);

        return dictionary;
    }

    public ComScoreMediaType getMediaType() {
        return mediaType;
    }

    public static class Builder {
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
        private boolean advertisementLoad = false;
        private String digitalAirdate;
        private String tvAirdate;
        private String stationTitle;
        private String c3;
        private String c4;
        private String c6;
        private boolean completeEpisode = false;
        private String feedType;

        public Builder(ComScoreMediaType mediaType) {
            this.mediaType = mediaType;
        }

        public Builder setUniqueContentId(String uniqueContentId) {
            this.uniqueContentId = uniqueContentId;
            return this;
        }

        public Builder setPublisherBrandName(String publisherBrandName) {
            this.publisherBrandName = publisherBrandName;
            return this;
        }

        public Builder setProgramTitle(String programTitle) {
            this.programTitle = programTitle;
            return this;
        }

        public Builder setProgramId(String programId) {
            this.programId = programId;
            return this;
        }

        public Builder setEpisodeTitle(String episodeTitle) {
            this.episodeTitle = episodeTitle;
            return this;
        }

        public Builder setEpisodeId(String episodeId) {
            this.episodeId = episodeId;
            return this;
        }

        public Builder setEpisodeSeasonNumber(String episodeSeasonNumber) {
            this.episodeSeasonNumber = episodeSeasonNumber;
            return this;
        }

        public Builder setEpisodeNumber(String episodeNumber) {
            this.episodeNumber = episodeNumber;
            return this;
        }

        public Builder setContentGenre(String contentGenre) {
            this.contentGenre = contentGenre;
            return this;
        }

        public Builder setAdvertisementLoad(boolean advertisementLoad) {
            this.advertisementLoad = advertisementLoad;
            return this;
        }

        public Builder setDigitalAirdate(String digitalAirdate) {
            this.digitalAirdate = digitalAirdate;
            return this;
        }

        public Builder setTvAirdate(String tvAirdate) {
            this.tvAirdate = tvAirdate;
            return this;
        }

        public Builder setStationTitle(String stationTitle) {
            this.stationTitle = stationTitle;
            return this;
        }

        public Builder setC3(String c3) {
            this.c3 = c3;
            return this;
        }

        public Builder setC4(String c4) {
            this.c4 = c4;
            return this;
        }

        public Builder setC6(String c6) {
            this.c6 = c6;
            return this;
        }

        public Builder setCompleteEpisode(boolean completeEpisode) {
            this.completeEpisode = completeEpisode;
            return this;
        }

        public Builder setFeedType(String feedType) {
            this.feedType = feedType;
            return this;
        }

        public ComScoreMetadata build() {
            return new ComScoreMetadata(mediaType, uniqueContentId, publisherBrandName, programTitle, programId, episodeTitle, episodeId, episodeSeasonNumber, episodeNumber, contentGenre, advertisementLoad, digitalAirdate, tvAirdate, stationTitle, c3, c4, c6, completeEpisode, feedType);
        }

    }
}
