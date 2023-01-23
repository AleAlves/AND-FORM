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
                is FormCheckVO -> {
                    it.isSelected = input.isSelected
                }
                is FormTextVO -> {
                    it.inputError = input.error
                    it.inputText = input.value
                    it.checkBox?.isSelected = input.isSelected
                }
            }
        }
        onNotifyUpdateAt.invoke(input.position)
    }

    override fun listenFormUpdates(input: (Int) -> Unit, validation: (Boolean) -> Unit) {
        onNotifyUpdateAt = input
        onValidation = validation
    }
}