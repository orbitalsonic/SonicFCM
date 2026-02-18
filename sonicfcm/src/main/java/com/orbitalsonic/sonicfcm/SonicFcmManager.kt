package com.orbitalsonic.sonicfcm

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.orbitalsonic.sonicfcm.notification.NotificationChannelHelper

object SonicFcmManager {
    fun setupFcm(context: Context, topic: String) {
        NotificationChannelHelper.create(context)
        FirebaseMessaging.getInstance().apply {
            subscribeToTopic(topic)
        }
    }

    fun removeFCM(topic: String){
        FirebaseMessaging.getInstance().apply {
            unsubscribeFromTopic(topic)
        }
    }
}