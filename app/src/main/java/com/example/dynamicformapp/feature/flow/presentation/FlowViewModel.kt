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

    private var steps: MutableList<StepVO> = mutableListOf()
    private val formLiveData = intoMediator<FlowState.Steps>()
    private var progressLiveData = intoMediator<FlowState.Progress>()
    private var position = -1

    fun onNext() {
        if (position < steps.size.minus(1)) {
            position++
            addStep()
        }
    }

    fun onPrevious() {
        if (position > 0) {
            position--
            if (steps[position].returnable) {
                removeStep()

            } else {
                onPrevious()
            }
        } else {
            formLiveData.postValue(FlowState.Steps.Finish)
        }
    }

    fun remove(vararg idSet: String) {
        idSet.map { id ->
            steps = steps.filter { it.id != id }.toMutableList()
        }
    }

    fun getFlows() {
        interactor.fetchFormSteps().let {
            steps.addAll(it.toMutableList())
            onNext()
        }
    }

    private fun addStep() {
        updateProgress()
        formLiveData.postValue(FlowState.Steps.ToNext(steps[position]))
    }

    private fun removeStep() {
        updateProgress()
        formLiveData.postValue(FlowState.Steps.ToPrevious(steps[position]))
    }

    private fun updateProgress() {
        progressLiveData.postValue(FlowState.Progress.OnUpdate(getProgress()))
    }

    private fun getProgress() =
        position.plus(1).toDouble()
            .div(steps.size.toDouble())
            .times(100)
            .toInt()

    sealed class FlowState : ViewState {


        sealed class Steps : FlowState() {
            data class ToNext(val vo: StepVO) : Steps()
            data class ToPrevious(val vo: StepVO) : Steps()
            object Finish : Steps()
        }

        sealed class Progress : FlowState() {
            data class OnUpdate(val progress: Int) : Progress()
        }
    }
}