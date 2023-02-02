package com.example.dynamicformapp.core.presentation


import androidx.lifecycle.ViewModel
import com.example.dynamicformapp.core.presentation.ui.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<T : ViewState> : ViewModel() {

    abstract val initialState: T

    private val _uiState: MutableStateFlow<T> by lazy { MutableStateFlow(initialState) }

    val state: StateFlow<T> get() = _uiState

    protected fun setViewState(newState: T) {
        _uiState.value = newState
    }
}