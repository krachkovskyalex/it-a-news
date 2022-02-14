package com.krachkovsky.it_anews.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.krachkovsky.it_anews.domain.usecase.GetNewsUseCase

class NewsViewModel(private val getNewsUseCase: GetNewsUseCase) : ViewModel() {

    val pagingFlow = Pager(
        PagingConfig(
            pageSize = PAGE_SIZE,
            initialLoadSize = PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) { NewsPagingSource(getNewsUseCase) }
        .flow
        .cachedIn(viewModelScope)

    companion object {
        private const val PAGE_SIZE = 20
        private const val PREFETCH_DISTANCE = 5
    }
}