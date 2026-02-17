package com.orbitalsonic.sonicfcm.notification

import com.orbitalsonic.sonicfcm.enums.NotificationType
import com.orbitalsonic.sonicfcm.models.NotificationPayload

object NotificationPayloadMapper {

    fun from(data: Map<String, String>): NotificationPayload? {

        val type = data["type"]?.let {
            runCatching { NotificationType.valueOf(it) }.getOrNull()
        }

        val title = data["title"] ?: return null
        val body = data["body"] ?: return null
        val icon = data["icon"]
        val feature = data["feature"]

        return when (type) {

            NotificationType.GENERAL ->
                NotificationPayload.General(
                    title = title,
                    body = body,
                    icon = icon,
                    feature = feature
                )

            NotificationType.EXTERNAL_LINK ->
                NotificationPayload.ExternalLink(
                    title = title,
                    body = body,
                    icon = icon,
                    feature = feature,
                    url = data["url"] ?: return null
                )

            else -> NotificationPayload.General(
                title = title,
                body = body,
                icon = icon,
                feature = feature
            )
        }
    }
}