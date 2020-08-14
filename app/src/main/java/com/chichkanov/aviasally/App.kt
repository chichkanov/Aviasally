package com.chichkanov.aviasally

import android.app.Application
import com.chichkanov.aviasally.di.DI
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initDagger()
        initTimber()
    }

    private fun initDagger() = DI.init(this)

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}