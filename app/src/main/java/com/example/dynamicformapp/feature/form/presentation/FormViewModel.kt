package com.example.dynamicformapp.feature.form.presentation

import androidx.lifecycle.viewModelScope
import com.example.dynamicformapp.core.presentation.BaseViewModel
import com.example.dynamicformapp.core.presentation.ui.ViewState
import com.example.dynamicformapp.feature.form.domain.model.*
import kotlinx.coroutines.*

interface FormActions {
    fun loadForms()
    fun onSetupForms() {}
    fun onInput(input: FormIO)
    fun onOutput(input: FormIO)
}

abstract class FormViewModel : BaseViewModel<FormViewModel.FormState>(), FormActions {

    private var formsVO: ArrayList<FormVO> = arrayListOf()

    private val formLiveData = intoMediator<FormState>()
    private val outputLiveData = intoMediator<FormState.Field>()
    private val buttonLiveData = intoMediator<FormState.Button>()

    protected abstract fun setupValidations()

    protected abstract fun getValidations(): Boolean

    fun initForms(vararg forms: FormVO) = launch {
        formsVO.apply {
            clear()
            forms.map {
                add(it)
            }
        }
        onSetupForms()
        setupValidations()
        onFormValidation()
    }

    override fun onSetupForms() = launch {
        formLiveData.value = FormState.OnLoadForm(formsVO)
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
        formsVO.mapIndexed { index, vo ->
            if (vo is FormRadioVO) {
                vo.isSelected = vo == formVO && input.isSelected
            }
            notifyOutputAt(index)
        }
    }

    private fun notifyOutputAt(position: Int) = launch {
        outputLiveData.value = (FormState.Field.OnFieldOutput(position))
    }

    fun updateFormFields(asyncBlockingQueue: suspend CoroutineScope.() -> Unit) = launch {
        asyncBlockingQueue.invoke(this)
        notifyAllFields()
    }

    private fun notifyAllFields() = launch {
        outputLiveData.value = FormState.Field.OnUpdateFields
    }

    private fun onFormValidation() = launch {
        buttonLiveData.value = FormState.Button.OnValidation(getValidations())
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