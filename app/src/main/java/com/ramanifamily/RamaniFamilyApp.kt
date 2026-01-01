package com.ramanifamily

import android.app.Application
import com.ramanifamily.data.remote.AppModule

class RamaniFamilyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppModule.init(this)
    }
}
