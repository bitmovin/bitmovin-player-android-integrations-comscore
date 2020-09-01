package com.bitmovin.player.integration.comscore

data class ComScoreConfiguration(
    val publisherId: String,
    val applicationName: String,
    var userConsent: ComScoreUserConsent = ComScoreUserConsent.UNKNOWN,
    val secureTransmission: Boolean = false,
    val childDirectedAppMode: Boolean = false,
    val debug: Boolean = false
)
