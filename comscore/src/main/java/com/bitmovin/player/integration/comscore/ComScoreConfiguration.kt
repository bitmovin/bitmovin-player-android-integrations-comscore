package com.bitmovin.player.integration.comscore

data class ComScoreConfiguration(
    val publisherId: String,
    val applicationName: String,
    var userConsent: ComScoreUserConsent = ComScoreUserConsent.UNKNOWN,
    val enableSecureTransmission: Boolean = false,
    val enableDirectedApplicationMode: Boolean = false,
    val debug: Boolean = false
)
