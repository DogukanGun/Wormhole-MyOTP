package com.dag.check24products

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    companion object {
        lateinit var appInstance: MainApplication
        var baseUrl = "https://app.check24.de/"
    }
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }
}