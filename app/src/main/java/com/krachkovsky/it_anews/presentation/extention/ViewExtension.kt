package com.krachkovsky.it_anews.presentation.extention

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
fun SwipeRefreshLayout.onRefreshListener() = callbackFlow {
    setOnRefreshListener {
        trySend(Unit)
    }

    awaitClose {
        setOnRefreshListener(null)
    }
}