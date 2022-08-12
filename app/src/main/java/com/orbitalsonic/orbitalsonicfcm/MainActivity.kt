package com.orbitalsonic.orbitalsonicfcm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.orbitalsonic.sonicfcm.SonicFCM

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SonicFCM.setupFCM(this, packageName)
    }
}