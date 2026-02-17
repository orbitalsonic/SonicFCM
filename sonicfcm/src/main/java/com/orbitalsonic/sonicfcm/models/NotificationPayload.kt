package com.orbitalsonic.sonicfcm.models

sealed class NotificationPayload(
    open val title: String,
    open val body: String,
    open val icon: String? = null,
    open val feature:String? = null
) {
    data class General(
        override val title: String,
        override val body: String,
        override val icon: String? = null,
        override val feature: String? = null
    ) : NotificationPayload(title, body,icon,feature)

    data class ExternalLink(
        override val title: String,
        override val body: String,
        override val icon: String? = null,
        override val feature: String? = null,
        val url: String
    ) : NotificationPayload(title, body,icon,feature)
}
