package com.krachkovsky.it_anews.presentation.koin

import com.krachkovsky.it_anews.data.api.NewsApiFactory
import com.krachkovsky.it_anews.data.repository.NewsRepositoryImpl
import com.krachkovsky.it_anews.domain.repository.NewsRepository
import com.krachkovsky.it_anews.domain.usecase.GetNewsUseCase
import com.krachkovsky.it_anews.presentation.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single { NewsApiFactory().provideApi() }
}

val getNewsUseCaseModule = module {
    factory { GetNewsUseCase(get()) }
}

val viewModelModule = module {
    viewModel { NewsViewModel(get()) }
}

val repositoryModule = module {
    factory<NewsRepository> { NewsRepositoryImpl(get()) }
}