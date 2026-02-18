[![](https://jitpack.io/v/orbitalsonic/SonicFCM.svg)](https://jitpack.io/#orbitalsonic/SonicFCM)

# SonicFCM 🚀

SonicFCM is a lightweight Android library built on top of [Firebase Cloud Messaging](https://firebase.google.com/docs/cloud-messaging) (FCM) that makes receiving and displaying push notifications extremely simple.

# 📦 Installation

## Step 1 --- Add Firebase to your App

Follow the official Firebase guide:
https://firebase.google.com/docs/android/setup

Make sure you have added: - `google-services.json` - Google Services
Gradle plugin - Firebase dependencies

### Project-level build.gradle
``` gradle
id 'com.google.gms.google-services' version 'latest-version' apply false
```

### App-level build.gradle

``` gradle
id 'com.google.gms.google-services'
```

------------------------------------------------------------------------

## Step 2 --- Add JitPack Repository

Add in `settings.gradle`:

``` gradle
repositories {
    google()
    mavenCentral()
    maven { url "https://jitpack.io" }
}
```

------------------------------------------------------------------------

## Step 3 --- Add Dependency

Add in app-level `build.gradle`: latest version [![](https://jitpack.io/v/orbitalsonic/SonicFCM.svg)](https://jitpack.io/#orbitalsonic/SonicFCM)

``` gradle
implementation 'com.github.orbitalsonic:SonicFCM:3.1.3'
```

------------------------------------------------------------------------

# 🚀 Usage

## Minimal Setup

### ✅ Subscribe to Topic

Call inside `Application` class or `MainActivity`:

``` kotlin
SonicFcmManager.setupFcm(context, "YourTopicName")
```

### ❌ Remove Topic Subscription

``` kotlin
SonicFcmManager.removeFCM("YourTopicName")
```

------------------------------------------------------------------------
# 📩 Sending Notifications

You can send notifications using:

1.  Firebase Console\
2.  Postman (HTTP v1 API)

------------------------------------------------------------------------

# 🔐 Sending Notification via Postman (HTTP v1 API)

1.  Enable **Firebase Cloud Messaging API** in Google Cloud Console\
2.  Generate `service-account.json` from Service Accounts\
3.  Use it to generate access token

⚠️ NEVER upload `service-account.json` publicly.

![alt text](https://github.com/orbitalsonic/SonicFCM/blob/master/Screenshots/firebase_screen.png?raw=true)

------------------------------------------------------------------------

## Android Example --- Generate Access Token (DEBUG ONLY)

Add `service-account.json` inside the `assets` folder:

``` kotlin
private fun setupFirebaseMessaging() {
    if (BuildConfig.DEBUG) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val credentials = assets.open("service-accounts.json").use { inputStream ->
                    GoogleCredentials.fromStream(inputStream)
                        .createScoped(listOf("https://www.googleapis.com/auth/firebase.messaging"))
                }
                credentials.refreshIfExpired()
                val token = credentials.accessToken.tokenValue

                Log.i("accessTokenTAG", "ACCESS-TOKEN = $token")
            } catch (e: Exception) {
                Log.e("accessTokenTAG", "ACCESS-TOKEN = $e")
            }
        }
    }
}
```

------------------------------------------------------------------------

## ⚠️ Security Disclaimer

This method is **DEBUG ONLY**.

-   Do NOT commit `service-account.json`
-   Do NOT ship it in production
-   Use a secure backend server in production
-   Delete the file after generating token
-   NEVER upload it publicly

------------------------------------------------------------------------

## 📬 Postman Request

### POST URL

    https://fcm.googleapis.com/v1/projects/YOUR_PROJECT_ID/messages:send

### Headers

    Authorization: Bearer YOUR_ACCESS_TOKEN
    Content-Type: application/json

------------------------------------------------------------------------

## 📦 Sample Payloads

### General Notification

``` json
{
  "message": {
    "topic": "myTopicName",
    "data": {
      "type": "GENERAL",
      "title": "Hello from SonicFCM",
      "body": "This is a test notification",
      "icon": "https://yourdomain.com/icon.png",
      "feature": "https://yourdomain.com/banner.png"
    }
  }
}
```

### External Link Notification

``` json
{
  "message": {
    "topic": "YourTopicName",
    "data": {
      "type": "EXTERNAL_LINK",
      "title": "Hello from SonicFCM",
      "body": "This is a test notification",
      "icon": "https://yourdomain.com/icon.png",
      "feature": "https://yourdomain.com/banner.png",
      "url": "https://yourdomain.com"
    }
  }
}
```

------------------------------------------------------------------------

### Postman Screen
![alt text](https://github.com/orbitalsonic/SonicFCM/blob/master/Screenshots/postman_screen.png?raw=true)

------------------------------------------------------------------------

# 🔔 Notification Permission (Android 13+)

Starting from **Android 13 (API level 33)**, apps must request notification permission at runtime in order to receive and display notifications. Without this permission, notifications will not appear on the device.

------------------------------------------------------------------------

# 📄 License

Copyright 2022 OrbitalSonic

Licensed under the Apache License, Version 2.0 (the "License"); you may
not use this file except in compliance with the License. You may obtain
a copy of the License at:

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

