package com.orbitalsonic.sonicfcm.notification

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class SonicMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        val payload = NotificationPayloadMapper.from(message.data)
            ?: return

        NotificationSender.send(this, payload)
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }
}