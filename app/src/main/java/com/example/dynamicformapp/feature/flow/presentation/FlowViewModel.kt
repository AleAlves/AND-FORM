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
) : BaseViewModel<FormViewModel.FormState>() {

    private var steps = mutableListOf<StepVO>()

    private val _state = MutableLiveData<FlowState>()
    val viewState: LiveData<FlowState> = _state

    init {
        interactor.getStartupStep().let {
            steps = it.toMutableList()
            _state.value = FlowState.OnLoadSteps(steps)
        }
    }

    fun onNext(position: Int) {
        _state.value = FlowState.OnForwardStep(position)
    }

    fun onPrevious(position: Int) {
        if (position >= 0)
            if (!steps[position].returnable) {
                onPrevious(position - 1)
            } else {
                _state.value = FlowState.OnBackwardStep(position)
            }
    }

    fun remove(id: String) {
        with(steps) {
            steps = this.filter { vo -> vo.flowId != id && vo.id != id }.toMutableList()
            _state.value = FlowState.OnUpdate(steps)
        }
    }

    fun getFlows() {
        reset()
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
            interactor.fetchFormSteps().let {
                steps.addAll(it.toMutableList())
                _state.postValue(FlowState.OnLoadSteps(steps))
            }
        }
    }

    private fun reset() {
        steps.removeAll { it.flowId != "A" }
        _state.postValue(FlowState.OnRemoveSteps(steps))
    }

    sealed class FlowState : ViewState {
        data class OnLoadSteps(val steps: List<StepVO>) : FlowState()
        data class OnRemoveSteps(val steps: List<StepVO>) : FlowState()
        data class OnRemoveStepAt(val position: Int) : FlowState()
        data class OnForwardStep(val position: Int) : FlowState()
        data class OnBackwardStep(val position: Int) : FlowState()
        data class OnUpdate(val steps: List<StepVO>) : FlowState()
    }
}