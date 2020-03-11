package com.bitmovin.player.integration.comscore.util

import com.bitmovin.player.integration.comscore.ComScoreMediaType
import com.comscore.streaming.ContentType

fun ComScoreMediaType.contentType(): Int = when (this) {
    ComScoreMediaType.LIVE -> ContentType.LIVE
    ComScoreMediaType.USER_GENERATED_SHORT_FORM_ON_DEMAND -> ContentType.USER_GENERATED_SHORT_FORM_ON_DEMAND
    ComScoreMediaType.USER_GENERATED_LONG_FORM_ON_DEMAND -> ContentType.USER_GENERATED_LONG_FORM_ON_DEMAND
    ComScoreMediaType.USER_GENERATED_LIVE -> ContentType.USER_GENERATED_LIVE
    ComScoreMediaType.BUMPER -> ContentType.BUMPER
    ComScoreMediaType.LONG_FORM_ON_DEMAND -> ContentType.LONG_FORM_ON_DEMAND
    ComScoreMediaType.SHORT_FORM_ON_DEMAND -> ContentType.SHORT_FORM_ON_DEMAND
    else -> ContentType.OTHER
}
