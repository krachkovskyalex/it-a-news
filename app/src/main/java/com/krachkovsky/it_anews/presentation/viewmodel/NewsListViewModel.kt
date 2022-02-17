package com.krachkovsky.it_anews.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.krachkovsky.it_anews.domain.usecase.GetNewsListUseCase
import com.krachkovsky.it_anews.presentation.pagingsource.NewsListPagingSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest

class NewsListViewModel(private val getNewsListUseCase: GetNewsListUseCase) : ViewModel() {

    private val newsCategory = MutableStateFlow("general")

    val newsFlow = newsCategory
        .debounce(SELECT_DEBOUNCE)
        .flatMapLatest { category ->
            getNews(category)
        }
        .cachedIn(viewModelScope)

    private fun getNews(category: String) = Pager(
        PagingConfig(
            pageSize = PAGE_SIZE,
            initialLoadSize = PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) { NewsListPagingSource(getNewsListUseCase, category) }
        .flow

    fun onCategoryChanged(category: String) {
        newsCategory.value = category
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val PREFETCH_DISTANCE = 5

        private const val SELECT_DEBOUNCE = 500L
    }
}