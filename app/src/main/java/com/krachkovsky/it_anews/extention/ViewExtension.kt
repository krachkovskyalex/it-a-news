package com.krachkovsky.it_anews.extention

import android.widget.Toolbar
import androidx.appcompat.widget.SearchView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.krachkovsky.it_anews.R
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun SwipeRefreshLayout.onRefreshListener() = callbackFlow {
    setOnRefreshListener {
        trySend(Unit)
    }

    awaitClose {
        setOnRefreshListener(null)
    }
}

fun Toolbar.onSearchQueryChanged() = callbackFlow {
    val searchView = menu.findItem(R.id.action_search).actionView as SearchView

    val queryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String): Boolean {
            trySend(newText)
            return true
        }
    }

    searchView.setOnQueryTextListener(queryTextListener)

    awaitClose {
        searchView.setOnQueryTextListener(null)
    }
}