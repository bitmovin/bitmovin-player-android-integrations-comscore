package com.bitmovin.player.integration.comscore

class ComScoreMetadata(
    val mediaType: ComScoreMediaType,
    private val uniqueContentId: String? = null,
    private val publisherBrandName: String? = null,
    private val programTitle: String? = null,
    private val programId: String? = null,
    private val episodeTitle: String? = null,
    private val episodeId: String? = null,
    private val episodeSeasonNumber: String? = null,
    private val episodeNumber: String? = null,
    private val contentGenre: String? = null,
    private val advertisementLoad: Boolean = false,
    private val digitalAirdate: String? = null,
    private val tvAirdate: String? = null,
    private val stationTitle: String? = null,
    private val c3: String? = null,
    private val c4: String? = null,
    private val c6: String? = null,
    private val completeEpisode: Boolean = false,
    private val feedType: String? = null
) {

    fun toMap(): Map<String, String?> = mutableMapOf(
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
    ).apply {
        if (completeEpisode) {
            put("ns_st_ce", "1")
        }
        if (advertisementLoad) {
            put("ns_st_ia", "1")
        }
    }
}
