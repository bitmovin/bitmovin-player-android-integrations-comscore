package com.bitmovin.player.integration.comscore

class ComScoreMetadata(val mediaType: ComScoreMediaType) {

    var uniqueContentId: String? = null
    var publisherBrandName: String? = null
    var programTitle: String? = null
    var programId: String? = null
    var episodeTitle: String? = null
    var episodeId: String? = null
    var episodeSeasonNumber: String? = null
    var episodeNumber: String? = null
    var contentGenre: String? = null
    var advertisementLoad = false
    var digitalAirdate: String? = null
    var tvAirdate: String? = null
    var stationTitle: String? = null
    var c3: String? = null
    var c4: String? = null
    var c6: String? = null
    var completeEpisode = false
    var feedType: String? = null

    fun toDictionary(): MutableMap<String, String?> {
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
