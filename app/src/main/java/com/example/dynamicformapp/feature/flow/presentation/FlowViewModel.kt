package com.example.dynamicformapp.feature.flow.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dynamicformapp.core.presentation.BaseViewModel
import com.example.dynamicformapp.core.presentation.ui.ViewState
import com.example.dynamicformapp.feature.flow.domain.FlowInteractor
import com.example.dynamicformapp.feature.flow.domain.model.StepVO
import com.example.dynamicformapp.feature.form.presentation.FormViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlowViewModel @Inject constructor(
    private val interactor: FlowInteractor
) : BaseViewModel<FlowViewModel.FlowState>() {

    private var steps = mutableListOf<StepVO>()

    init {
        interactor.getStartupStep().let {
            steps = it.toMutableList()
            setViewState(FlowState.OnLoadSteps(steps))
        }
    }

    fun onNext(position: Int) {
        setViewState(FlowState.OnForwardStep(position))
    }

    fun onPrevious(position: Int) {
        if (position >= 0)
            if (!steps[position].returnable) {
                onPrevious(position - 1)
            } else {
                setViewState(FlowState.OnBackwardStep(position))
            }
    }

    fun remove(id: String) {
        with(steps) {
            steps = this.filter { vo -> vo.flowId != id && vo.id != id }.toMutableList()
            setViewState(FlowState.OnUpdate(steps))
        }
    }

    fun getFlows() {
        reset()
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
            interactor.fetchFormSteps().let {
                steps.addAll(it.toMutableList())
                setViewState(FlowState.OnLoadSteps(steps))
            }
        }
    }

    private fun reset() {
        steps.removeAll { it.flowId != "A" }
        setViewState(FlowState.OnRemoveSteps(steps))
    }

    sealed class FlowState : ViewState {
        object Init : FlowState()
        data class OnLoadSteps(val steps: List<StepVO>) : FlowState()
        data class OnRemoveSteps(val steps: List<StepVO>) : FlowState()
        data class OnRemoveStepAt(val position: Int) : FlowState()
        data class OnForwardStep(val position: Int) : FlowState()
        data class OnBackwardStep(val position: Int) : FlowState()
        data class OnUpdate(val steps: List<StepVO>) : FlowState()
    }

    override val initialState: FlowState = FlowState.Init
}