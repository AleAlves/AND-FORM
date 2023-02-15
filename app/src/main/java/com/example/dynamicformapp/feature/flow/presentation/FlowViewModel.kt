package com.example.dynamicformapp.feature.flow.presentation

import androidx.lifecycle.viewModelScope
import com.example.dynamicformapp.core.presentation.BaseViewModel
import com.example.dynamicformapp.core.presentation.ui.ViewState
import com.example.dynamicformapp.feature.flow.domain.FlowInteractor
import com.example.dynamicformapp.feature.flow.domain.model.StepVO
import com.example.dynamicformapp.feature.form.presentation.FormViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlowViewModel @Inject constructor(
    private val interactor: FlowInteractor
) : BaseViewModel<FlowViewModel.FlowState>() {

    private var steps: MutableList<StepVO> = mutableListOf()
    private val formLiveData = intoMediator<FlowState.Steps>()
    private var progressLiveData = intoMediator<FlowState.Progress>()
    private var position = 0

    init {
        viewModelScope.launch {
            interactor.getStartupStep().let {
                with(steps) {
                    clear()
                    steps.addAll(it)
                    addStep()
                }
            }
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
        formLiveData.postValue(FlowState.Steps.AddStep(steps[position]))
    }

    private fun removeStep() {
        updateProgress()
        formLiveData.postValue(FlowState.Steps.RemoveStep(steps[position]))
    }

    private fun updateProgress() {
        progressLiveData.postValue(FlowState.Progress.OnUpdate(getProgress()))
    }

    private fun getProgress() =
        position.plus(0).toDouble()
            .div(steps.size.toDouble())
            .times(100)
            .toInt()

    sealed class FlowState : ViewState {

        sealed class Steps : FlowState() {
            data class AddStep(val vo: StepVO) : Steps()
            data class RemoveStep(val vo: StepVO) : Steps()
        }

        sealed class Progress : FlowState() {
            data class OnUpdate(val progress: Int) : Progress()
        }
    }
}