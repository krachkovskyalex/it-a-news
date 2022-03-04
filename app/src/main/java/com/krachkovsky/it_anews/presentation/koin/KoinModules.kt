package com.krachkovsky.it_anews.presentation.koin

import com.krachkovsky.it_anews.presentation.settings.manager.SharedPrefsManager
import com.krachkovsky.it_anews.presentation.viewmodel.NewsListViewModel
import com.krachkovsky.it_anews.presentation.viewmodel.NewsSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { NewsListViewModel(get()) }
    viewModel { NewsSearchViewModel(get()) }
}

val sharedPrefsModule = module {
    single { SharedPrefsManager(get()) }
}