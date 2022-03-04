package com.krachkovsky.it_anews.presentation.extention

fun String.toRequestParameter(): String {
    return when (this) {
        "все новости" -> "general"
        "all news" -> "general"
        "бизнес" -> "business"
        "развлечения" -> "entertainment"
        "здоровье" -> "health"
        "наука" -> "science"
        "спорт" -> "sports"
        "технологии" -> "technology"
        else -> this
    }
}