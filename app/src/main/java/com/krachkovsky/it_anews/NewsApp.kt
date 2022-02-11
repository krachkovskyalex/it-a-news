package com.krachkovsky.it_anews

import android.app.Application
import com.krachkovsky.it_anews.koin.networkModule
import com.krachkovsky.it_anews.koin.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@NewsApp)
            modules(networkModule, viewModelModule)
        }
    }
}