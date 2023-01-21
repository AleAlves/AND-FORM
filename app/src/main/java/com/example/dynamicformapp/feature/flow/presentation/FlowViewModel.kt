package com.example.dynamicformapp.feature.flow.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dynamicformapp.core.presentation.BaseViewModel
import com.example.dynamicformapp.core.presentation.ui.ViewState
import com.example.dynamicformapp.feature.flow.domain.FlowInteractor
import com.example.dynamicformapp.feature.flow.domain.model.StepVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlowViewModel @Inject constructor(
    private val interactor: FlowInteractor
) : BaseViewModel() {

    private var steps = mutableListOf<StepVO>()

    private val _state = MutableLiveData<FormState>()
    val viewState: LiveData<FormState> = _state

    init {
        interactor.getStartupStep().let {
            steps = it.toMutableList()
            _state.value = FormState.OnLoadSteps(steps)
        }
    }

    fun onNext(position: Int) {
        _state.value = FormState.OnForwardStep(position)
    }

    fun onPrevious(position: Int) {
        if (position >= 0)
            if (!steps[position].returnable) {
                onPrevious(position - 1)
            } else {
                _state.value = FormState.OnBackwardStep(position)
            }
    }

    fun remove(id: String) {
        with(steps) {
            steps = this.filter { vo -> vo.flowId != id && vo.id != id }.toMutableList()
            _state.value = FormState.OnUpdate(steps)
        }
    }

    fun getFlows() {
        reset()
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
            interactor.fetchFormSteps().let {
                steps.addAll(it.toMutableList())
                _state.postValue(FormState.OnLoadSteps(steps))
            }
        }
    }

    private fun reset() {
        steps.removeAll { it.flowId != "A" }
        _state.postValue(FormState.OnRemoveSteps(steps))
    }

    sealed class FormState : ViewState {
        data class OnLoadSteps(val steps: List<StepVO>) : FormState()
        data class OnRemoveSteps(val steps: List<StepVO>) : FormState()
        data class OnRemoveStepAt(val position: Int) : FormState()
        data class OnForwardStep(val position: Int) : FormState()
        data class OnBackwardStep(val position: Int) : FormState()
        data class OnUpdate(val steps: List<StepVO>) : FormState()
    }
}