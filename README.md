[![](https://jitpack.io/v/orbitalsonic/SonicFCM.svg)](https://jitpack.io/#orbitalsonic/SonicFCM)
# FCM

FCM is a [Firebase Cloud Messaging](https://firebase.google.com/docs/cloud-messaging) Android library that demonstrates registering your Android app for notifications and handling the receipt of a message. Example Send Data Message using the HTTP protocol with [Postman](https://www.postman.com/).

## Getting Started

### Step 1

[Add firebase to your Android App](https://firebase.google.com/docs/android/setup)

#### Note:
After completion of step one, your project should have a google-services.json file added to the root of your project along with the classpath, plugin, and dependencies

#### Classpath in project-level build.gradle
```
    classpath 'com.google.gms:google-services:latest-version'
```
or latest
```
    id 'com.google.gms.google-services' version 'latest-version' apply false
```

#### Plugin in App level build.gradle
```
    id 'com.google.gms.google-services'
```
#### Dependencies
no dependencies required

### Step 2

Add maven repository in project-level build.gradle or in the latest project setting.gradle file
```
    repositories {
        google()
        mavenCentral()
        maven { url "https://jitpack.io" }
    }
```  

### Step 3

Add FCM dependencies in App level build.gradle.
```
    dependencies {
             implementation 'com.github.orbitalsonic:SonicFCM:2.0.0-alpha-01'
    }
```  


### Step 4

Finally, initialize Firebase and setup FCM in the application class or your "MainActivity"

```
    SonicFCM.setupFCM(this, "YourTopicName")
```


### Remove

If you want to stop receiving notifications from the subscribed topic simply call.
```
    SonicFCM.removeFCM("YourTopicName")
```

# Send Data Messages using the HTTP protocol with POSTMAN

### Step 1

You have to copy the Legacy Server Key from Firebase Console > Project Settings > Cloud Messaging

#### Note:

Firebase has upgraded our server keys to a new version. You may continue to use your Legacy server key, but it is recommended that you upgrade to the newest version.
If anyone else is facing any issues then First Enabled "Firebase Cloud Messaging API" from the Firebase console.

![alt text](https://github.com/orbitalsonic/SonicFCM/blob/master/Screenshots/firebase_screen3.png?raw=true)

### Step 2

* Open Postman, click on Enter request URL textbox, enter Firebase URL
```
 https://fcm.googleapis.com/fcm/send
```
* Then change the request type to "POST"
* Now click on Header and add two params "Content-Type" and "Authorization"
```
 Content-Type: application/json
 Authorization: key=AAAAp5XtBPY:APA91bG_fypMd0j... //FCM SERVER KEY
```
* Now click on "Body" then select "Raw" and add value as JSON object like below
```
{
      "to":"/topics/YourTopicName", 
      "data":
      {
                "title": "My Application Title is Here",
	        "short_desc": "My Application Short Description is here",
	        "icon": "App Icon link is here",
	        "feature": "App Feature Image Link is here",
	        "package": "Promotional app package name is here"
      }
  }
```
### Postman Screen

#### Header

![alt text](https://github.com/orbitalsonic/SonicFCM/blob/master/Screenshots/postman_screen1.png?raw=true)

#### Body

![alt text](https://github.com/orbitalsonic/SonicFCM/blob/master/Screenshots/postman_screen2.png?raw=true)

### Note

These Three items are mandatory for notification
* title
* icon
* short_desc

These Three items are optional for notification
* long_desc
* feature
* package (in case of other app promotions)


# LICENSE

Copyright 2022 OrbitalSonic

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

