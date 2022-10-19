package com.ratushny.modulotech.presentation.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<State> : ViewModel() {

    private val initialState by lazy { createInitialState() }

    protected val screenStateMutable = MutableLiveData(initialState)
    val screenState: LiveData<State>
        get() = screenStateMutable

    fun attach() {
        onAttached()
    }

    protected abstract fun createInitialState(): State

    protected abstract fun onAttached()
}

