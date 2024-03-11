package com.orbitalsonic.sonicfcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.util.concurrent.atomic.AtomicInteger

/**
 * @Author: Muhammad Yaqoob
 * @Date: 13,Feb,2024.
 * @Accounts
 *      -> https://github.com/orbitalsonic
 *      -> https://www.linkedin.com/in/myaqoob7
 */
class SonicFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        //Data available
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            //fetch data into variables
            val icon = remoteMessage.data["icon"]
            val title = remoteMessage.data["title"]
            val shortDesc = remoteMessage.data["short_desc"]
            val featureImage = remoteMessage.data["feature"]
            val packageName = remoteMessage.data["package"]

            //send notification
            if (icon == null || title == null || shortDesc == null) {
                return
            } else {
                Handler(this.mainLooper).post {
                    sendNotification(icon, title, shortDesc, featureImage, packageName)
                }
            }
        }
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    private fun sendNotification(
        icon: String,
        title: String,
        shortDesc: String,
        image: String?,
        storePackage: String?
    ) {
        //Open PlayStore
        val mIntent = if (storePackage != null && isCrossPromotionPackage(storePackage)) {
            setStoreIntent(storePackage)
        } else {
            openApp(packageName)
        }

        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getActivity(
                this,
                0,
                mIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
            )
        } else {
            PendingIntent.getActivity(this, 0, mIntent, PendingIntent.FLAG_ONE_SHOT)
        }

        //Make Remote Views For text
        val remoteViews = RemoteViews(packageName, R.layout.firebase_notification_view)
        remoteViews.setTextViewText(R.id.tv_title, title)
        remoteViews.setTextViewText(R.id.tv_short_desc, shortDesc)


        if (storePackage != null && isCrossPromotionPackage(storePackage)) {
            remoteViews.setViewVisibility(R.id.tv_ad, View.VISIBLE)
        }

        //Notification Parameters
        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.notification_icon)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setOnlyAlertOnce(true)
            .setCustomContentView(remoteViews)
            .setCustomBigContentView(remoteViews)
            .setAutoCancel(true)

        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                getString(R.string.default_notification_channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        //Build Notification
        val notificationID = getNextInt()
        notificationManager.notify(notificationID, notificationBuilder.build())

        //Set Images into remoteViews
        try {
            // Load icon image using Glide
            Glide.with(this)
                .asBitmap()
                .load(icon)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        remoteViews.setImageViewBitmap(R.id.iv_icon, resource)
                        notificationManager.notify(notificationID, notificationBuilder.build())
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // Do nothing
                    }
                })

            if (image != null) {
                // Load feature image using Glide
                Glide.with(this)
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

                        override fun onLoadCleared(placeholder: Drawable?) {
                            // Do nothing
                        }
                    })
            }
        } catch (ignore: IllegalArgumentException) {
        } catch (ignore: java.lang.Exception) {
        } catch (ignore: IllegalStateException) {
        } catch (ignore: Exception) {
        }
    }

    private fun openApp(storePackage: String): Intent {
        return try {
            packageManager.getLaunchIntentForPackage(storePackage) ?: setStoreIntent(storePackage)
        } catch (e: Exception) {
            setStoreIntent(storePackage)
        }
    }


    private fun setStoreIntent(storePackage: String): Intent {
        return try {
            Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$storePackage"))
        } catch (e: ActivityNotFoundException) {
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$storePackage")
            )
        }
    }

    private fun isCrossPromotionPackage(mPackage: String): Boolean {
        return packageName != mPackage
    }


    companion object {
        private const val TAG = "SonicFirebaseMsgService"
        private val number = AtomicInteger()
        fun getNextInt(): Int {
            return number.incrementAndGet()
        }
    }
}