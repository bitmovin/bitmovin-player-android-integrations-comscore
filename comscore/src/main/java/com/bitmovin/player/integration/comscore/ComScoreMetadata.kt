package com.bitmovin.player.integration.comscore

class ComScoreMetadata(
    val mediaType: ComScoreMediaType,
    private var uniqueContentId: String? = null,
    private var publisherBrandName: String? = null,
    private var programTitle: String? = null,
    private var programId: String? = null,
    private var episodeTitle: String? = null,
    private var episodeId: String? = null,
    private var episodeSeasonNumber: String? = null,
    private var episodeNumber: String? = null,
    private var contentGenre: String? = null,
    private var advertisementLoad: Boolean = false,
    private var digitalAirdate: String? = null,
    private var tvAirdate: String? = null,
    private var stationTitle: String? = null,
    private var c3: String? = null,
    private var c4: String? = null,
    private var c6: String? = null,
    private var completeEpisode: Boolean = false,
    private var feedType: String? = null
) {

    fun toMap(): MutableMap<String, String?> {
        val map = mutableMapOf(
            "ns_st_ci" to uniqueContentId,
            "ns_st_pu" to publisherBrandName,
            "ns_st_pr" to programTitle,
            "ns_st_tpr" to programId,
            "ns_st_ep" to episodeTitle,
            "ns_st_tep" to episodeId,
            "ns_st_sn" to episodeSeasonNumber,
            "ns_st_en" to episodeNumber,
            "ns_st_ge" to contentGenre,
            "ns_st_ddt" to digitalAirdate,
            "ns_st_tdt" to tvAirdate,
            "ns_st_st" to stationTitle,
            "c3" to c3,
            "c4" to c4,
            "c6" to c6,
            "ns_st_ft" to feedType
        )

        if (advertisementLoad) {
            map["ns_st_ia"] = "1"
        }
        if (completeEpisode) {
            map["ns_st_ce"] = "1"
        }

        return map
    }
}
