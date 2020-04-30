package com.bitmovin.player.integration.comscore.util

import com.comscore.Analytics

fun notifyHiddenEvent(publisherId: String, label: String, value: String) {
    val publisherConfig = Analytics.getConfiguration().getPublisherConfiguration(publisherId)
    publisherConfig.setPersistentLabel(label, value)
    Analytics.notifyHiddenEvent()
}

fun notifyHiddenEvents(publisherId: String, labels: Map<String, String>) {
    val publisherConfig = Analytics.getConfiguration().getPublisherConfiguration(publisherId)
    labels.forEach { publisherConfig.setPersistentLabel(it.key, it.value) }
    Analytics.notifyHiddenEvent()
}
