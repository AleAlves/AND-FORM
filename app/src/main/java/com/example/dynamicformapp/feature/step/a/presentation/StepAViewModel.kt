package com.example.dynamicformapp.feature.step.a.presentation

import com.example.dynamicformapp.core.presentation.BaseViewModel
import com.example.dynamicformapp.core.presentation.ui.ViewState
import com.example.dynamicformapp.feature.form.model.FormInput
import com.example.dynamicformapp.feature.form.model.FormVO
import com.example.dynamicformapp.feature.step.a.domain.StepAInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StepAViewModel @Inject constructor(
    private val interactor: StepAInteractor
) : BaseViewModel() {

    init {
        state.value = StepAState.OnLoad(interactor.getForms())
        onFormOutput()
    }

    fun doLogin() = interactor.doLoginCall()

    fun onFormInput(value: FormInput) {
        interactor.onInput(value)
    }

    private fun onFormOutput() {
        interactor.listenFormUpdates({ position ->
            state.value = StepAState.OnUpdate(position)
        }, { isValid ->
            state.value = StepAState.OnButtonStatus(isValid)
        })
    }

    sealed class StepAState : ViewState {
        data class OnLoad(val forms: List<FormVO>) : StepAState()
        data class OnUpdate(val position: Int) : StepAState()
        data class OnButtonStatus(val isEnabled: Boolean) : StepAState()
    }
}