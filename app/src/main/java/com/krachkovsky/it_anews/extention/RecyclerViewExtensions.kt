package com.krachkovsky.it_anews.extention

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun RecyclerView.addHorizontalSpaceDecoration(space: Int) {
    addItemDecoration(
        object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position = parent.getChildAdapterPosition(view)
                if (position != 0 && position != parent.adapter?.itemCount) {
                    outRect.top = space
                }
            }
        }
    )
}

fun RecyclerView.addPaginationScrollListener(
    layoutManager: LinearLayoutManager,
    isLoading: Boolean,
    itemsToLoad: Int,
    onLoadMore: () -> Unit
) {
    addOnScrollListener(PagingScrollListener(layoutManager, isLoading, itemsToLoad, onLoadMore))
}

fun RecyclerView.onPaginationScrollListener(
    layoutManager: LinearLayoutManager,
    isLoading: Boolean,
    itemsToLoad: Int
) = callbackFlow {
    val scrollListener = PagingScrollListener(layoutManager, isLoading, itemsToLoad) {
        trySend(Unit)
    }
    addOnScrollListener(scrollListener)

    awaitClose {
        removeOnScrollListener(scrollListener)
    }
}

class PagingScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val isLoading: Boolean,
    private val itemsToLoad: Int,
    private val onLoadMore: () -> Unit
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val totalItemCount = layoutManager.itemCount
        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

        if (!isLoading && dy != 0 && totalItemCount <= (lastVisibleItem + itemsToLoad)) {
            recyclerView.post(onLoadMore)
        }
    }
}