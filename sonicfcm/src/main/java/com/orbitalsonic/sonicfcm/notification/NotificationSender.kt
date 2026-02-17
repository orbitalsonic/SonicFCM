package com.orbitalsonic.sonicfcm.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.orbitalsonic.sonicfcm.R
import com.orbitalsonic.sonicfcm.models.NotificationPayload

object NotificationSender {

    fun send(context: Context, payload: NotificationPayload) {
        showNotification(context, payload)
    }

    private fun showNotification(context: Context, payload: NotificationPayload) {
        val intent = NotificationNavigation.create(context, payload)

        val pendingIntent = PendingIntent.getActivity(
            context,
            System.currentTimeMillis().toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = context.getString(R.string.default_notification_channel_id)

        val remoteViews =
            RemoteViews(context.packageName, R.layout.firebase_notification_view)

        // Set title & body
        remoteViews.setTextViewText(R.id.tv_title, payload.title)
        remoteViews.setTextViewText(R.id.tv_body, payload.body)

        if (isSystemInDarkMode(context)) {
            // Dark mode → white text
            remoteViews.setTextColor(R.id.tv_title, Color.WHITE)
            remoteViews.setTextColor(R.id.tv_body, Color.WHITE)
        } else {
            // Light mode → black text
            remoteViews.setTextColor(R.id.tv_title, Color.BLACK)
            remoteViews.setTextColor(R.id.tv_body, Color.BLACK)
        }

        // Get images from payload
        val icon = payload.icon
        val image = payload.feature

        // Icon visibility
        if (icon.isNullOrEmpty()) {
            remoteViews.setViewVisibility(R.id.iv_icon, View.GONE)
        } else {
            remoteViews.setViewVisibility(R.id.iv_icon, View.VISIBLE)
        }

        // Feature visibility
        if (image.isNullOrEmpty()) {
            remoteViews.setViewVisibility(R.id.iv_feature, View.GONE)
        } else {
            remoteViews.setViewVisibility(R.id.iv_feature, View.VISIBLE)
        }

        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_fcm_notification_small)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setCustomContentView(remoteViews)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationID = (System.currentTimeMillis() % Int.MAX_VALUE).toInt()

        val notification = notificationBuilder.build()

        notificationManager.notify(notificationID, notification)

        // ✅ Load Images with Glide
        try {

            // Load icon image
            if (!icon.isNullOrEmpty()) {
                Glide.with(context)
                    .asBitmap()
                    .load(icon)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            remoteViews.setViewVisibility(R.id.iv_icon, View.VISIBLE)
                            remoteViews.setImageViewBitmap(R.id.iv_icon, resource)
                            notificationManager.notify(notificationID, notificationBuilder.build())
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {}
                    })
            }

            // Load feature image
            if (!image.isNullOrEmpty()) {
                Glide.with(context)
                    .asBitmap()
                    .load(image)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            remoteViews.setViewVisibility(R.id.iv_feature, View.VISIBLE)
                            remoteViews.setImageViewBitmap(R.id.iv_feature, resource)
                            notificationManager.notify(notificationID, notificationBuilder.build())
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {}
                    })
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun isSystemInDarkMode(context: Context): Boolean {
        return when (context.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> true
            Configuration.UI_MODE_NIGHT_NO -> false
            else -> false
        }
    }

}
