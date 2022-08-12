package com.orbitalsonic.sonicfcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

class SonicFCM {
    companion object {
        fun setupFCM(context: Context, topic: String) {
            initializeFirebase(context)
            createChannelForFCM(context)
            FirebaseMessaging.getInstance().subscribeToTopic(topic)
        }

        fun removeFCM(topic: String) {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
        }

        private fun initializeFirebase(context: Context) {
            try {
                FirebaseApp.initializeApp(context)
            } catch (e: Exception) {
                Log.e("TAG", "onCreate: ${e.message}")
            }
        }

        private fun createChannelForFCM(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelId = context.getString(R.string.default_notification_channel_id)
                val channelName = context.getString(R.string.default_notification_channel_name)
                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(
                    NotificationChannel(
                        channelId,
                        channelName, NotificationManager.IMPORTANCE_DEFAULT
                    )
                )
            }
        }
    }
}