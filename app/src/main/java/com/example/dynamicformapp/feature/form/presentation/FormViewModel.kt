package com.example.dynamicformapp.feature.form.presentation

import androidx.lifecycle.viewModelScope
import com.example.dynamicformapp.core.presentation.BaseViewModel
import com.example.dynamicformapp.core.presentation.ui.ViewState
import com.example.dynamicformapp.feature.form.domain.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface FormActions {
    fun loadForms()
    fun onSetupForms() {}
    fun onInput(input: FormIO)
    fun onOutput(input: FormIO)
}

abstract class FormViewModel : BaseViewModel<FormViewModel.FormState>(), FormActions {

    private var forms: ArrayList<FormVO> = arrayListOf()

    protected abstract fun setupValidations()

    protected abstract fun getValidations(): Boolean

    fun initForms(vararg forms: FormVO) {
        forms.map {
            this.forms.add(it)
        }
        setViewState(FormState.OnInitForms(forms.toList()))
        onSetupForms()
        setupValidations()
        mainValidation()
    }

    override fun onInput(input: FormIO) {
        forms[input.position].onInput.invoke(input)
    }

    override fun onOutput(input: FormIO) {
        forms[input.position].let {
            when (it) {
                is FormTextVO -> onTextOutput(it, input)
                is FormRadioVO -> onRadioOutput(it, input)
                is FormCheckVO -> onCheckOutput(it, input)
            }
        }
        mainValidation()
    }

    private fun onTextOutput(formVO: FormTextVO, input: FormIO) {
        formVO.error = input.error
        formVO.text = input.value
        formVO.checkBox?.isSelected = input.isSelected
        notifyOutputAt(input.position)
    }

    private fun onCheckOutput(formVO: FormCheckVO, input: FormIO) {
        formVO.isSelected = input.isSelected
        notifyOutputAt(input.position)
    }

    private fun onRadioOutput(formVO: FormRadioVO, input: FormIO) {
        forms.mapIndexed { index, vo ->
            if (vo is FormRadioVO) {
                vo.isSelected = vo == formVO && input.isSelected
                notifyOutputAt(index)
            }
        }
    }

    private fun notifyOutputAt(position: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            setViewState(FormState.OnFormOutput(position))
        }
    }

    fun updateFormFields(asyncBlockingQueue: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            asyncBlockingQueue()
            setViewState(FormState.OnUpdatingForms)
        }
    }

    private fun mainValidation() {
        viewModelScope.launch(Dispatchers.Main) {
            setViewState(FormState.OnValidation(getValidations()))
        }
    }

    open class FormState : ViewState {
        object Init : FormState()
        data class OnInitForms(val forms: List<FormVO>) : FormState()
        data class OnFormOutput(val position: Int) : FormState()
        data class OnValidation(val isValid: Boolean) : FormState()
        object OnUpdatingForms : FormState()
    }
}