package com.ramanifamily

import android.app.Application
import com.ramanifamily.data.remote.AppModule
//import dagger.hilt.android.HiltAndroidApp

//@HiltAndroidApp
class RamaniFamilyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppModule.init(this)
    }
}
