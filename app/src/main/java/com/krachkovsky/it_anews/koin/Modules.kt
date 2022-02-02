package com.krachkovsky.it_anews.koin

import com.krachkovsky.it_anews.retrofit.NewsApiFactory
import com.krachkovsky.it_anews.ui.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single { NewsApiFactory().provideApi() }
}

val viewModelModule = module {
    viewModel { NewsViewModel(get()) }
}