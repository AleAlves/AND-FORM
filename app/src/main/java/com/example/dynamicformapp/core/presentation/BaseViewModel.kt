package com.example.dynamicformapp.core.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dynamicformapp.core.presentation.ui.ViewState

abstract class BaseViewModel<T : ViewState> : ViewModel() {

    private val _state: MutableLiveData<T> = MutableLiveData()
    val state: LiveData<T> = _state

    protected fun setViewState(newState: T) {
        _state.value = newState
    }
}