package krachkovsky.it_anews_data.koin

import krachkovsky.it_anews_data.api.NewsApiFactory
import krachkovsky.it_anews_data.database.NewsDB
import krachkovsky.it_anews_data.repository.NewsApiRepositoryImpl
import krachkovsky.it_anews_data.repository.NewsDBRepositoryImpl
import krachkovsky.it_anews_domain.repository.NewsApiRepository
import krachkovsky.it_anews_domain.repository.NewsDBRepository
import krachkovsky.it_anews_domain.usecase.*
import org.koin.dsl.module

val networkModule = module {
    single { NewsApiFactory().provideApi() }
}

val roomModule = module {
    single { NewsDB.getInstance(get()) }
}

val getNewsUseCaseModule = module {
    factory { GetNewsListUseCase(get()) }
    factory { GetNewsSearchUseCase(get()) }
    factory { GetNewsSavedUseCase(get()) }
    factory { InsertArticleToDBUseCase(get()) }
    factory { DeleteArticleFromDBUseCase(get()) }
}

val repositoryModule = module {
    factory<NewsApiRepository> { NewsApiRepositoryImpl(get()) }
    factory<NewsDBRepository> { NewsDBRepositoryImpl(get()) }
}