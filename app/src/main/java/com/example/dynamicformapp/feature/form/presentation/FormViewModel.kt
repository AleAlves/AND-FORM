package com.example.dynamicformapp.feature.form.presentation

import com.example.dynamicformapp.core.presentation.BaseViewModel
import com.example.dynamicformapp.core.presentation.ui.ViewState
import com.example.dynamicformapp.feature.form.domain.model.*
import kotlinx.coroutines.*

interface FormActions {
    fun onLoadForms()
    fun onSetupForms() {}
    fun onInput(input: FormIO)
    fun onOutput(input: FormIO)
}

abstract class FormViewModel : BaseViewModel<FormViewModel.FormState>(), FormActions {

    private var formsVO: List<FormVO> = listOf()

    private val formLiveData = intoMediator<FormState>()
    private val outputLiveData = intoMediator<FormState.Field>()
    private val buttonLiveData = intoMediator<FormState.Button>()

    protected abstract fun onValidations()

    protected abstract fun getValidations(): Boolean

    fun initForms(vararg forms: FormVO) = launch {
        formsVO = forms.toList()
        onSetupForms()
        onValidations()
        onFormValidation()
        formLiveData.postValue(FormState.OnLoadForm(formsVO))
    }

    override fun onInput(input: FormIO) = launch {
        formsVO[input.position].onInput.invoke(input)
    }

    override fun onOutput(input: FormIO) = launch {
        formsVO[input.position].let {
            when (it) {
                is FormTextVO -> onTextOutput(it, input)
                is FormRadioVO -> onRadioOutput(it, input)
                is FormCheckVO -> onCheckOutput(it, input)
            }
        }
        onFormValidation()
    }

    private fun onTextOutput(formVO: FormTextVO, input: FormIO) = launch {
        formVO.error = input.error
        formVO.text = input.value
        formVO.checkBox?.isSelected = input.isSelected
        notifyOutputAt(input.position)
    }

    private fun onCheckOutput(formVO: FormCheckVO, input: FormIO) = launch {
        formVO.isSelected = input.isSelected
        notifyOutputAt(input.position)
    }

    private fun onRadioOutput(formVO: FormRadioVO, input: FormIO) = launch {
        formsVO.map { vo ->
            if (vo is FormRadioVO) {
                vo.isSelected = vo == formVO && input.isSelected
            }
        }
        notifyAllFields()
    }

    private fun notifyOutputAt(position: Int) {
        outputLiveData.postValue(FormState.Field.OnFieldOutput(position))
    }

    fun updateFormFields(asyncBlockingQueue: suspend CoroutineScope.() -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            asyncBlockingQueue.invoke(this)
            notifyAllFields()
        }
    }

    private fun notifyAllFields() {
        outputLiveData.postValue(FormState.Field.OnUpdateFields)
    }

    private fun onFormValidation() {
        buttonLiveData.postValue(FormState.Button.OnValidation(getValidations()))
    }

    open class FormState : ViewState {

        data class OnLoadForm(val forms: List<FormVO>) : FormState()

        sealed class Field : FormState() {
            object OnUpdateFields : Field()
            data class OnFieldOutput(val position: Int) : Field()
        }

        sealed class Button : FormState() {
            data class OnValidation(val isValid: Boolean) : Button()
        }
    }
}