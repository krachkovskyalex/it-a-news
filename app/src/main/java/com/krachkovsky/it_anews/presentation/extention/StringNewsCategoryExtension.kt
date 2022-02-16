package com.krachkovsky.it_anews.presentation.extention

fun String.toRequestParameter(): String {
    return when (this) {
        "главная" -> "general"
        "бизнес" -> "business"
        "развлечения" -> "entertainment"
        "здоровье" -> "health"
        "наука" -> "science"
        "спорт" -> "sports"
        "технологии" -> "technology"
        else -> this
    }
}