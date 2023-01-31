package com.example.dynamicformapp.feature.form.presentation

import com.example.dynamicformapp.core.presentation.BaseViewModel
import com.example.dynamicformapp.core.presentation.ui.ViewState
import com.example.dynamicformapp.feature.form.domain.model.*
import com.example.dynamicformapp.feature.form.model.*

interface FormActions {
    fun onInput(input: FormData)
    fun onOutput(input: FormData)
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
        setupValidations()
    }

    override fun onInput(input: FormData) {
        forms[input.position].onInput.invoke(input)
    }

    override fun onOutput(input: FormData) {
        forms[input.position].let {
            when (it) {
                is FormTextVO -> applyTextChange(it, input)
                is FormRadioVO -> applyRadioChange(it, input)
                is FormCheckVO -> applyCheckChange(it, input)
            }
        }
        validation()
    }

    private fun applyTextChange(formVO: FormTextVO, input: FormData) {
        formVO.error = input.error
        formVO.text = input.value
        formVO.checkBox?.isSelected = input.isSelected
        outputAt(input.position)
    }

    private fun applyCheckChange(formVO: FormCheckVO, input: FormData) {
        formVO.isSelected = input.isSelected
        outputAt(input.position)
    }

    private fun applyRadioChange(formVO: FormRadioVO, input: FormData) {
        forms.mapIndexed { index, vo ->
            if (vo is FormRadioVO) {
                vo.isSelected = vo == formVO && input.isSelected
                outputAt(index)
            }
        }
    }

    private fun outputAt(position: Int) {
        setViewState(FormState.OnFormOutput(position))
    }

    private fun validation() {
        setViewState(FormState.OnValidation(getValidations()))
    }

    open class FormState : ViewState {
        data class OnInitForms(val forms: List<FormVO>) : FormState()
        data class OnFormOutput(val position: Int) : FormState()
        data class OnValidation(val isValid: Boolean) : FormState()
    }
}