package com.example.mobiledatausage

import android.app.Application
import com.example.mobiledatausage.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MobileDataUsageApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MobileDataUsageApplication)
            modules(appModule)
        }
    }
}