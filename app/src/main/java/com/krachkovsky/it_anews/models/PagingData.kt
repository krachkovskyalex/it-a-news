package com.krachkovsky.it_anews.models

sealed class PagingData<out T> {
    data class Content<T>(val data: T) : PagingData<T>()
    object Loading : PagingData<Nothing>()
}