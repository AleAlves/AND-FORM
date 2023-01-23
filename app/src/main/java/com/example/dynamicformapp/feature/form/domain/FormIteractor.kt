package com.example.dynamicformapp.feature.form.domain

import com.example.dynamicformapp.feature.form.model.*

interface FormInteractor {
    fun getForms(): List<FormVO>
    fun onInput(input: FormInput)
    fun onOutput(input: FormInput)
    fun listenFormUpdates(input: (Int) -> Unit, validation: (Boolean) -> Unit)
}

abstract class BaseFormIteractor : FormInteractor {
    protected abstract var inputForms: ArrayList<FormVO>
    protected abstract var onValidation: ((Boolean) -> Unit)
    protected abstract var onNotifyUpdateAt: ((Int) -> Unit)
    protected abstract fun performValidation()

    override fun getForms(): List<FormVO> = inputForms

    override fun onInput(input: FormInput) {
        inputForms[input.position].onInput.invoke(input)
    }

    override fun onOutput(input: FormInput) {
        performValidation()
        inputForms[input.position].let {
            when (it) {
                is FormTextVO -> applyTextChange(it, input)
                is FormRadioVO -> applyRadioChange(it, input)
                is FormCheckVO -> applyCheckChange(it, input)
            }
        }
    }

    override fun listenFormUpdates(input: (Int) -> Unit, validation: (Boolean) -> Unit) {
        onNotifyUpdateAt = input
        onValidation = validation
    }

    private fun applyTextChange(formVO: FormTextVO, input: FormInput) {
        formVO.error = input.error
        formVO.text = input.value
        formVO.checkBox?.isSelected = input.isSelected
        onNotifyUpdateAt.invoke(input.position)
    }

    private fun applyCheckChange(formVO: FormCheckVO, input: FormInput) {
        formVO.isSelected = input.isSelected
        onNotifyUpdateAt.invoke(input.position)
    }

    private fun applyRadioChange(formVO: FormRadioVO, input: FormInput) {
        val wow = inputForms.mapIndexed { index, vo ->
            if (vo is FormRadioVO) {
                vo.isSelected = false
                onNotifyUpdateAt.invoke(index)
            }
        }
        formVO.isSelected = input.isSelected
        onNotifyUpdateAt.invoke(input.position)
    }
}