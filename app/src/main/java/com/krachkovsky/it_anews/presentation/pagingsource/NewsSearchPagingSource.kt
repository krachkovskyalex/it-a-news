package com.krachkovsky.it_anews.presentation.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState

class NewsSearchPagingSource(
    private val getNewsSearchUseCase: krachkovsky.it_anews_domain.usecase.GetNewsSearchUseCase,
    private val q: String
) : PagingSource<Int, krachkovsky.it_anews_domain.models.Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, krachkovsky.it_anews_domain.models.Article> {

        val nextPage = params.key ?: 1
        val loadSize = params.loadSize.coerceAtMost(MAX_PAGE_SIZE)

        return runCatching { getNewsSearchUseCase(nextPage, loadSize, q) }
            .fold(
                onSuccess = { news ->
                    val articles = news.articles
                    LoadResult.Page(
                        data = articles,
                        prevKey = if (nextPage != 1) {
                            nextPage - 1
                        } else {
                            null
                        },
                        nextKey = if (articles.size == loadSize && articles.size * nextPage < MAX_RESULT) {
                            nextPage + 1
                        } else {
                            null
                        }
                    )
                },
                onFailure = {
                    LoadResult.Error(it)
                }
            )
    }

    override fun getRefreshKey(state: PagingState<Int, krachkovsky.it_anews_domain.models.Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val MAX_PAGE_SIZE = 100
        private const val MAX_RESULT = 100
    }
}