package com.krachkovsky.it_anews.presentation.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import krachkovsky.it_anews_domain.models.Article
import krachkovsky.it_anews_domain.usecase.GetNewsSavedUseCase

class NewsSavedPagingSource(
    private val getNewsSavedUseCase: GetNewsSavedUseCase,
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {

        val nextPage = params.key ?: 1
        val loadSize = params.loadSize.coerceAtMost(MAX_PAGE_SIZE)

        return runCatching { getNewsSavedUseCase() }
            .fold(
                onSuccess = { news ->
                    LoadResult.Page(
                        data = news,
                        prevKey = if (nextPage != 1) {
                            nextPage - 1
                        } else {
                            null
                        },
                        nextKey = if (news.size == loadSize) {
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

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val MAX_PAGE_SIZE = 100
    }
}