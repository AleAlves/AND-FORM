package com.example.dynamicformapp.core.presentation


import androidx.lifecycle.*
import com.example.dynamicformapp.core.presentation.ui.ViewState

abstract class BaseViewModel<T : ViewState> : ViewModel() {

    private val _uiState: MediatorLiveData<T> by lazy { MediatorLiveData() }

    val state: MediatorLiveData<T> get() = _uiState

    protected fun setViewState(newState: T) {
        _uiState.postValue(newState)
    }

    protected fun <U : ViewState> intoMediator(): MutableLiveData<U> = MutableLiveData<U>().apply {
        _uiState.addSource(this) { t -> _uiState.value = (t as T) }
    }

}