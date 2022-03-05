package com.krachkovsky.it_anews.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.krachkovsky.it_anews.presentation.pagingsource.NewsSearchPagingSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import krachkovsky.it_anews_domain.usecase.GetNewsSearchUseCase

class NewsSearchViewModel(private val getNewsSearchUseCase: GetNewsSearchUseCase) :
    ViewModel() {

    private val searchQuery = MutableStateFlow("")

    private var oldQuery = ""

    val searchNews = searchQuery
        .debounce(SEARCH_DEBOUNCE)
        .flatMapLatest { query ->
            if (query.isNotBlank()) getSearchNews(query)
            else flowOf(PagingData.empty())
        }
        .cachedIn(viewModelScope)

    private fun getSearchNews(query: String) = Pager(
        PagingConfig(
            pageSize = PAGE_SIZE,
            initialLoadSize = PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) { NewsSearchPagingSource(getNewsSearchUseCase, query) }
        .flow

    fun onQueryChanger(query: String) {
        if (query != "") oldQuery = query
        if (searchQuery.value != oldQuery && query != "") searchQuery.value = query
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val PREFETCH_DISTANCE = 5

        private const val SEARCH_DEBOUNCE = 2000L
    }
}