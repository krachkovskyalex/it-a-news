package com.krachkovsky.it_anews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.krachkovsky.it_anews.retrofit.NewsApi

class NewsViewModel(private val newsApi: NewsApi) : ViewModel() {

    val pagingFlow = Pager(
        PagingConfig(
            pageSize = PAGE_SIZE,
            initialLoadSize = PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) { NewsPagingSource(newsApi) }
        .flow
        .cachedIn(viewModelScope)

    companion object {
        private const val PAGE_SIZE = 20
        private const val PREFETCH_DISTANCE = 5
    }
}