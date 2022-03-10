package com.krachkovsky.it_anews.presentation.extention

import android.content.Context
import android.content.res.Configuration
import com.krachkovsky.it_anews.presentation.settings.model.LanguageMode
import java.util.*

fun Context.applySelectedAppLanguage(language: LanguageMode): Context {
    val newConfig = Configuration(resources.configuration)
    Locale.setDefault(language.locale)
    newConfig.setLocale(language.locale)

    return createConfigurationContext(newConfig)
}