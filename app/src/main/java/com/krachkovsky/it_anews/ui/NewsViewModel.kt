package com.krachkovsky.it_anews.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krachkovsky.it_anews.models.Article
import com.krachkovsky.it_anews.models.NewsEverything
import com.krachkovsky.it_anews.retrofit.NewsApi
import com.krachkovsky.it_anews.util.Constants.QUERY_LIMIT_RESULTS
import com.krachkovsky.it_anews.util.Constants.QUERY_PAGE_SIZE
import com.krachkovsky.it_anews.util.Constants.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class NewsViewModel(private val newsApi: NewsApi) : ViewModel() {

    private var currentPage = 1
    var isLoading = false

    private val _newsFlow = MutableSharedFlow<List<Article>>()
    val newsFlow: Flow<List<Article>> = _newsFlow.asSharedFlow()

    private val _errorsFlow = MutableSharedFlow<Throwable>()
    val errorsFlow: Flow<Throwable> = _errorsFlow.asSharedFlow()

    init {
        loadNews()
    }

    fun onLoadMore() {
        isLoading = false
        loadNews()
    }

    fun onRefresh() {
        isLoading = false
        currentPage = 1
        loadNews()
        Log.d(TAG, "NewsViewModel -> onRefresh")
    }

    private fun loadNews() {
        if (isLoading) return

        viewModelScope.launch(Dispatchers.IO) {
            isLoading = true
            try {
                val news = newsApi.getAllNews(
                    page = currentPage,
                    pageSize = QUERY_PAGE_SIZE
                )
                _newsFlow.emit(news.articles)
                if (currentPage * QUERY_PAGE_SIZE < QUERY_LIMIT_RESULTS) {
                    currentPage++
                }
            } catch (e: Throwable) {
                _errorsFlow.emit(e)
            }
        }
    }
}