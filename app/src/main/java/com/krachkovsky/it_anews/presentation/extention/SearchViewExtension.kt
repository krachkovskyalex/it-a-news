package com.krachkovsky.it_anews.presentation.extention

import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun SearchView.onTextChanged() = callbackFlow {
    this@onTextChanged.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            trySend(newText ?: "")
            return true
        }
    })

    awaitClose {
        this@onTextChanged.setOnQueryTextListener(null)
    }
}