package com.example.dynamicformapp.feature.flow.presentation

import com.example.dynamicformapp.core.presentation.BaseViewModel
import com.example.dynamicformapp.core.presentation.ui.ViewState
import com.example.dynamicformapp.feature.flow.domain.FlowInteractor
import com.example.dynamicformapp.feature.flow.domain.model.StepVO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FlowViewModel @Inject constructor(
    private val interactor: FlowInteractor
) : BaseViewModel<FlowViewModel.FlowState>() {

    private var steps = mutableListOf<StepVO>()
    private var position = 0

    init {
        interactor.getStartupStep().let {
            steps.addAll(it)
            addStep()
        }
    }

    fun onNext() {
        if (position < steps.size) {
            position++
            addStep()
        }
    }

    fun onPrevious() {
        if (position > 0) {
            removeStep()
            position--
        }
    }

    fun remove(vararg idSet: String) {
        idSet.map { id ->
            steps = steps.filter { it.id != id && it.flowId != id }.toMutableList()
        }
    }

    fun getFlows() {
        interactor.fetchFormSteps().let {
            steps.addAll(it.toMutableList())
            onNext()
        }
    }

    private fun addStep() {
        setViewState(FlowState.AddStep(steps[position]))
    }


    private fun removeStep() {
        setViewState(FlowState.RemoveStep(steps[position]))
    }

    sealed class FlowState : ViewState {
        object Init : FlowState()
        data class AddStep(val vo: StepVO) : FlowState()
        data class RemoveStep(val vo: StepVO) : FlowState()
    }

    override val initialState: FlowState = FlowState.Init
}