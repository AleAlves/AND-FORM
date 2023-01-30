package com.example.dynamicformapp.feature.form.presentation

import androidx.lifecycle.viewModelScope
import com.example.dynamicformapp.core.presentation.BaseViewModel
import com.example.dynamicformapp.core.presentation.ui.ViewState
import com.example.dynamicformapp.feature.form.domain.FormInteractor
import com.example.dynamicformapp.feature.form.model.FormData
import com.example.dynamicformapp.feature.form.model.FormVO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


interface FormViewModelAction {
    fun onInputValue(value: String) {}
}

abstract class FormViewModel(
    private val interactor: FormInteractor
) : BaseViewModel<FormViewModel.FormState>(), FormViewModelAction {

    var inputValue = ""

    init {
        setViewState(FormState.OnInitForms(interactor.getForms()))
        onFormOutput()
    }

    override fun onInputValue(value: String) {
        inputValue = value
    }

    private fun onFormOutput() {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.listenFormUpdates(
                ::outputAt,
                ::validation
            )
        }
    }

    private fun outputAt(position: Int, value: String) {
        onInputValue(value)
        setViewState(FormState.OnFormOutput(position))
    }

    private fun validation(isValid: Boolean) {
        setViewState(FormState.OnValidation(isValid))
    }

    fun onFormInput(value: FormData) {
        interactor.onInput(value)
    }

    open class FormState : ViewState {
        data class OnInitForms(val forms: List<FormVO>) : FormState()
        data class OnFormOutput(val position: Int) : FormState()
        data class OnValidation(val isEnabled: Boolean) : FormState()
    }
}