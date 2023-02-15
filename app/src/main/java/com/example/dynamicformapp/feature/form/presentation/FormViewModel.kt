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

    private var formsVO: ArrayList<FormVO> = arrayListOf()

    private val formLiveData = intoMediator<FormState.Field>()
    private val buttonLiveData = intoMediator<FormState.Button>()

    protected abstract fun setupValidations()

    protected abstract fun getValidations(): Boolean

    fun initForms(vararg forms: FormVO) {
        viewModelScope.launch {
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
    }

    override fun onSetupForms() {
        formLiveData.value = FormState.Field.OnInitForms(this.formsVO)
    }

    override fun onInput(input: FormIO) {
        viewModelScope.launch {
            formsVO[input.position].onInput.invoke(input)
        }
    }

    override fun onOutput(input: FormIO) {
        viewModelScope.launch {
            formsVO[input.position].let {
                when (it) {
                    is FormTextVO -> onTextOutput(it, input)
                    is FormRadioVO -> onRadioOutput(it, input)
                    is FormCheckVO -> onCheckOutput(it, input)
                }
            }
            onFormValidation()
        }
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
        formsVO.mapIndexed { index, vo ->
            if (vo is FormRadioVO) {
                vo.isSelected = vo == formVO && input.isSelected
            }
            notifyOutputAt(index)
        }
    }

    private fun notifyOutputAt(position: Int) {
        formLiveData.value = FormState.Field.OnFormOutput(position)
    }

    fun updateFormFields(asyncBlockingQueue: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            asyncBlockingQueue.invoke(this)
            notifyAllFields()
        }
    }

    private fun notifyAllFields() {
        formLiveData.value = FormState.Field.OnUpdatingForms
    }

    private fun onFormValidation() {
        buttonLiveData.value = FormState.Button.OnValidation(getValidations())
    }

    open class FormState : ViewState {

        sealed class Field : FormState() {
            object OnUpdatingForms : Field()
            data class OnInitForms(val forms: List<FormVO>) : Field()
            data class OnFormOutput(val position: Int) : Field()
        }

        sealed class Button : FormState() {
            data class OnValidation(val isValid: Boolean) : Button()
        }
    }
}