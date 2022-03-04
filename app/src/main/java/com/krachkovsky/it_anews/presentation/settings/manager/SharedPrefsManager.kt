package com.krachkovsky.it_anews.presentation.settings.manager

import android.content.Context
import com.krachkovsky.it_anews.presentation.settings.model.DayNightMode
import com.krachkovsky.it_anews.presentation.settings.model.LanguageMode

class SharedPrefsManager(context: Context) {

    private val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var dayNightMode: DayNightMode by enumPrefs(KEY_DAY_NIGHT_MODE, DayNightMode.SYSTEM)
    var languageMode: LanguageMode by enumPrefs(KEY_LANGUAGE_MODE, LanguageMode.EN)

    private inline fun <reified E : Enum<E>> enumPrefs(keyMode: String, defaultValue: E) =
        PrefsDelegate(
            sharedPrefs,
            getValue = { getString(keyMode, null)?.let(::enumValueOf) ?: defaultValue },
            setValue = { putString(keyMode, it.name) }
        )

    companion object {
        private const val PREFS_NAME = "prefs"

        private const val KEY_DAY_NIGHT_MODE = "day_night"
        private const val KEY_LANGUAGE_MODE = "lang"
    }
}