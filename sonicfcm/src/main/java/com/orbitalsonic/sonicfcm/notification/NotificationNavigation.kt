package com.orbitalsonic.sonicfcm.notification

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.orbitalsonic.sonicfcm.models.NotificationPayload

object NotificationNavigation {

    fun create(context: Context, payload: NotificationPayload): Intent? {

        return when (payload) {

            is NotificationPayload.General ->
                context.packageManager.getLaunchIntentForPackage(context.packageName)

            is NotificationPayload.ExternalLink ->
                Intent(Intent.ACTION_VIEW, payload.url.toUri())

        }
    }
}
