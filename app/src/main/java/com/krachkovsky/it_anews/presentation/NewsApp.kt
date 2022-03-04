package com.krachkovsky.it_anews.presentation

import android.app.Application
import com.krachkovsky.it_anews.presentation.koin.sharedPrefsModule
import com.krachkovsky.it_anews.presentation.koin.viewModelModule
import krachkovsky.it_anews_data.koin.getNewsUseCaseModule
import krachkovsky.it_anews_data.koin.networkModule
import krachkovsky.it_anews_data.koin.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@NewsApp)
            modules(
                networkModule,
                viewModelModule,
                repositoryModule,
                getNewsUseCaseModule,
                sharedPrefsModule
            )
        }
    }
}