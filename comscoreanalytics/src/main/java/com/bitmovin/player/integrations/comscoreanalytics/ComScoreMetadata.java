package com.bitmovin.player.integrations.comscoreanalytics;

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
    boolean advertisementLoad = false;
    private String digitalAirdate;
    private String tvAirdate;
    private String stationTitle;
    private String c3;
    private String c4;
    private String c6;
    boolean completeEpisode = false;
    private String feedType;

    public ComScoreMetadata(ComScoreMediaType mediaType, String uniqueContentId, String publisherBrandName, String programTitle, String programId, String episodeTitle, String episodeId, String episodeSeasonNumber, String episodeNumber, String contentGenre, boolean advertisementLoad, String digitalAirdate, String tvAirdate, String stationTitle, String c3, String c4, String c6, boolean completeEpisode, String feedType) {
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

    ;

    public ComScoreMediaType getMediaType() {
        return mediaType;
    }
}
