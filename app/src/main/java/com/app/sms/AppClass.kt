package com.app.sms

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

import timber.log.Timber.*

@HiltAndroidApp
class AppClass : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(DebugTree())
    }
}