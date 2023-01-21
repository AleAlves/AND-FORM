package com.example.dynamicformapp.feature.step.b


import com.example.dynamicformapp.core.presentation.BaseViewModel
import com.example.dynamicformapp.core.presentation.ui.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StepBViewModel @Inject constructor() : BaseViewModel() {

    fun getName() {
        state.value = StepBState.OnLoadName("")
    }

    sealed class StepBState : ViewState {
        data class OnLoadName(val name: String) : StepBState()
    }
}