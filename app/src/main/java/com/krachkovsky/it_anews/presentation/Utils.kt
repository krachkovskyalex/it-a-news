package com.krachkovsky.it_anews.presentation

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.google.android.material.appbar.AppBarLayout

fun updateStatusBarInsets(view: View, appBar: AppBarLayout) {
    ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
        val inset = insets.getInsets(WindowInsetsCompat.Type.statusBars())
        appBar.updatePadding(
            top = inset.top,
        )
        insets
    }
}