package com.ratushny.modulotech.presentation.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<State> : ViewModel() {

    protected val _screenState by lazy { MutableLiveData<State>() }
    val screenState: LiveData<State>
        get() = _screenState

    open fun onAttached() {

    }

    open fun onDetached() {

    }
}

