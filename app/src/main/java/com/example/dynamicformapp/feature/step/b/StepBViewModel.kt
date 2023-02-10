package com.example.dynamicformapp.feature.step.b


import com.example.dynamicformapp.core.presentation.BaseViewModel
import com.example.dynamicformapp.core.presentation.ui.ViewState
import com.example.dynamicformapp.feature.flow.presentation.FlowViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StepBViewModel @Inject constructor() : BaseViewModel<StepBViewModel.StepBState>() {

    sealed class StepBState : ViewState {
        object Init : StepBState()
        data class OnLoadName(val name: String) : StepBState()
    }

    override val initialState: StepBState = StepBState.Init
}