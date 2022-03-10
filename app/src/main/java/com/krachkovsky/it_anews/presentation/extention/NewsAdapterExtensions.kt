package com.krachkovsky.it_anews.presentation.extention

import android.view.View
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.krachkovsky.it_anews.presentation.adapter.NewsAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy

suspend fun NewsAdapter.onLoad(
    progress: CircularProgressIndicator,
    swipeRefreshList: SwipeRefreshLayout,
    view: View
) {
    this
        .loadStateFlow
        .distinctUntilChangedBy { it.refresh }
        .collectLatest {
            val state = it.refresh

            progress.isVisible =
                !swipeRefreshList.isRefreshing && state == LoadState.Loading
            swipeRefreshList.isRefreshing =
                swipeRefreshList.isRefreshing && state == LoadState.Loading

            if (state is LoadState.Error) {
                Snackbar.make(
                    view,
                    state.error.localizedMessage ?: "Something went wrong",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction(android.R.string.ok) { this.retry() }
                    .show()
            }
        }
}