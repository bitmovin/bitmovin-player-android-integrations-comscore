package com.bitmovin.player.integration.comscore

data class ComScoreConfiguration(
    val publisherId: String,
    val publisherSecret: String,
    val applicationName: String,
    var userConsent: ComScoreUserConsent = ComScoreUserConsent.UNKNOWN,
    val isSecureTransmissionEnabled: Boolean = false,
    val isDebug: Boolean = false
)
