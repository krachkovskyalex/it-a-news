package com.krachkovsky.it_anews.presentation.settings.model

import java.util.*

enum class LanguageMode(
    val locale: Locale
) {
    EN(
        locale = Locale.ENGLISH
    ),
    RU(
        locale = Locale("ru")
    )
}