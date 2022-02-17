package com.krachkovsky.it_anews.presentation.extention

import android.view.View
import android.widget.AdapterView
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun AdapterView<*>.onCategoryChanged() = callbackFlow {
    this@onCategoryChanged.onItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                trySend(
                    parent?.getItemAtPosition(position)
                        .toString()
                        .lowercase()
                        .toRequestParameter()
                )
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

    awaitClose {
        this@onCategoryChanged.onItemSelectedListener = null
    }
}