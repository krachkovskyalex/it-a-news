package com.krachkovsky.it_anews.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.krachkovsky.it_anews.presentation.pagingsource.NewsSavedPagingSource
import krachkovsky.it_anews_domain.models.Article
import krachkovsky.it_anews_domain.usecase.DeleteArticleFromDBUseCase
import krachkovsky.it_anews_domain.usecase.GetNewsSavedUseCase
import krachkovsky.it_anews_domain.usecase.InsertArticleToDBUseCase

class NewsSavedViewModel(
    private val getNewsSavedUseCase: GetNewsSavedUseCase,
    private val insertArticleToDBUseCase: InsertArticleToDBUseCase,
    private val deleteArticleFromDBUseCase: DeleteArticleFromDBUseCase
) : ViewModel() {

    val savedNewsFlow = getSavedNews()

    private fun getSavedNews() = Pager(
        PagingConfig(
            pageSize = PAGE_SIZE,
            initialLoadSize = PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) { NewsSavedPagingSource(getNewsSavedUseCase) }
        .flow

    suspend fun insertArticleToDB(article: Article) = insertArticleToDBUseCase.invoke(article)

    suspend fun deleteArticleFromDB(article: Article) = deleteArticleFromDBUseCase.invoke(article)

    companion object {
        private const val PAGE_SIZE = 20
        private const val PREFETCH_DISTANCE = 5
    }
}