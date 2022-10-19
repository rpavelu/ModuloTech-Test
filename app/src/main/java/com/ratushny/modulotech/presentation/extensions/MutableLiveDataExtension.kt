package com.ratushny.modulotech.presentation.extensions

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.update(updateAction: (T) -> T) {
    value =
        value?.let(updateAction) ?: throw IllegalStateException("LiveData must have initial value")
}