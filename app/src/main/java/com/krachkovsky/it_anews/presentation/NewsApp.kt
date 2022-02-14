package com.krachkovsky.it_anews.presentation

import android.app.Application
import com.krachkovsky.it_anews.presentation.koin.getNewsUseCaseModule
import com.krachkovsky.it_anews.presentation.koin.networkModule
import com.krachkovsky.it_anews.presentation.koin.repositoryModule
import com.krachkovsky.it_anews.presentation.koin.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@NewsApp)
            modules(networkModule, viewModelModule, repositoryModule, getNewsUseCaseModule)
        }
    }
}