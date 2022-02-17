package com.krachkovsky.it_anews.presentation.koin

import com.krachkovsky.it_anews.data.api.NewsApiFactory
import com.krachkovsky.it_anews.data.repository.NewsRepositoryImpl
import com.krachkovsky.it_anews.domain.repository.NewsRepository
import com.krachkovsky.it_anews.domain.usecase.GetNewsListUseCase
import com.krachkovsky.it_anews.domain.usecase.GetNewsSearchUseCase
import com.krachkovsky.it_anews.presentation.settings.manager.SharedPrefsManager
import com.krachkovsky.it_anews.presentation.viewmodel.NewsListViewModel
import com.krachkovsky.it_anews.presentation.viewmodel.NewsSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single { NewsApiFactory().provideApi() }
}

val getNewsUseCaseModule = module {
    factory { GetNewsListUseCase(get()) }
    factory { GetNewsSearchUseCase(get()) }
}

val viewModelModule = module {
    viewModel { NewsListViewModel(get()) }
    viewModel { NewsSearchViewModel(get()) }
}

val repositoryModule = module {
    factory<NewsRepository> { NewsRepositoryImpl(get()) }
}

val sharedPrefsModule = module {
    single { SharedPrefsManager(get()) }
}