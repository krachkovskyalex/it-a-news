package krachkovsky.it_anews_data.koin

import krachkovsky.it_anews_data.api.NewsApiFactory
import krachkovsky.it_anews_data.repository.NewsRepositoryImpl
import krachkovsky.it_anews_domain.repository.NewsRepository
import krachkovsky.it_anews_domain.usecase.GetNewsListUseCase
import krachkovsky.it_anews_domain.usecase.GetNewsSearchUseCase
import org.koin.dsl.module

val networkModule = module {
    single { NewsApiFactory().provideApi() }
}

val getNewsUseCaseModule = module {
    factory { GetNewsListUseCase(get()) }
    factory { GetNewsSearchUseCase(get()) }
}

val repositoryModule = module {
    factory<NewsRepository> { NewsRepositoryImpl(get()) }
}