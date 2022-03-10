package com.krachkovsky.it_anews.presentation

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowCompat
import com.krachkovsky.it_anews.R
import com.krachkovsky.it_anews.presentation.extention.applySelectedAppLanguage
import com.krachkovsky.it_anews.presentation.settings.manager.SharedPrefsManager
import com.krachkovsky.it_anews.presentation.settings.model.DayNightMode
import org.koin.android.ext.android.inject

class NewsMainActivity : AppCompatActivity(R.layout.activity_main) {

    private val sharedPrefs: SharedPrefsManager by inject()

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase.applySelectedAppLanguage(sharedPrefs.languageMode))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor =
            ResourcesCompat.getColor(resources, R.color.primary_variant, theme)

        AppCompatDelegate.setDefaultNightMode(
            when (sharedPrefs.dayNightMode) {
                DayNightMode.DAY -> AppCompatDelegate.MODE_NIGHT_NO
                DayNightMode.NIGHT -> AppCompatDelegate.MODE_NIGHT_YES
                DayNightMode.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
        )
    }
}