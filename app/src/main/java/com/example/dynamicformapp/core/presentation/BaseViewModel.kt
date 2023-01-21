package com.example.dynamicformapp.core.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.dynamicformapp.core.presentation.ui.ViewState

abstract class BaseViewModel() : ViewModel() {

    internal val state: MutableLiveData<ViewState> = MutableLiveData()

    fun setViewState(state: ViewState) {
        this.state.postValue(state)
    }
}

inline fun <reified T> SavedStateHandle.getExtra(
    key: String
): Lazy<T> = lazy {
    if (keys().contains(key)) {
        get(key) as? T ?: throw ClassNotFoundException()
    } else {
        throw Exception()
    }
}