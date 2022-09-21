# OrbitalSonicFCM
[![](https://jitpack.io/v/orbitalsonic/OrbitalSonicFCM.svg)](https://jitpack.io/#orbitalsonic/OrbitalSonicFCM)

OrbitalSonicFCM is a [Firebase Cloud Messaging](https://firebase.google.com/docs/cloud-messaging) Android library that demonstrates registering your Android app for notifications and handling the receipt of a message using [Postman](https://www.postman.com/).

## Getting Started

### Step 1

[Add firebase to your Android App](https://firebase.google.com/docs/android/setup)

#### Note: 
After completion of step one, your project should have a google-services.json file added to the root of your project along with the classpath, plugin and dependecies

#### Classpath in project level build.gradle
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

Add maven repository in project level build.gradle or in latest project setting.gradle file
```
    repositories {
        google()
        mavenCentral()
        maven { url "https://jitpack.io" }
    }
```  

### Step 3

Add OrbitalSonicFCM dependencies in App level build.gradle.
```
    dependencies {
            implementation 'com.github.orbitalsonic:OrbitalSonicFCM:1.0.1
    }
```  


### Step 4

Finally intialize Firebase and setup FCM in application class or in your "MainActivity"

```
    SonicFCM.setupFCM(this, "YourTopicName")
```


### Remove

If you want to stop receiving notification from the subscribed topic simply call.
```
    SonicFCM.removeFCM("YourTopicName")
```



