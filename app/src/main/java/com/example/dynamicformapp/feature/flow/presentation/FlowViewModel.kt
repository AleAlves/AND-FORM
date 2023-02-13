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
            with(steps) {
                clear()
                steps.addAll(it)
            }
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
            position--
            removeStep()
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
        setViewState(FlowState.RemoveStep)
    }

    sealed class FlowState : ViewState {
        data class AddStep(val vo: StepVO) : FlowState()
        object RemoveStep : FlowState()
    }
}