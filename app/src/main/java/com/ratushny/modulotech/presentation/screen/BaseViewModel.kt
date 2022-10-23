package com.ratushny.modulotech.presentation.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<State> : ViewModel() {

    protected abstract val initialState: State

    protected val _screenState by lazy { MutableLiveData(initialState) }
    val screenState: LiveData<State>
        get() = _screenState

    fun attach() {
        onAttached()
    }

    protected abstract fun onAttached()
}

