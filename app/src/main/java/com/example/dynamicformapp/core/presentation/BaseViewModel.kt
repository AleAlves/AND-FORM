package com.example.dynamicformapp.core.presentation


import androidx.lifecycle.*
import com.example.dynamicformapp.core.presentation.ui.ViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel<T : ViewState> : ViewModel() {

    private val _uiState: MediatorLiveData<T> by lazy { MediatorLiveData() }

    val state: LiveData<T> get() = _uiState

    protected fun <U : ViewState> intoMediator(): MutableLiveData<U> = MutableLiveData<U>().apply {
        _uiState.addSource(this) { t -> _uiState.value = (t as T) }
    }

    fun launch(async: suspend CoroutineScope.() -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            async.invoke(this)
        }
    }
}