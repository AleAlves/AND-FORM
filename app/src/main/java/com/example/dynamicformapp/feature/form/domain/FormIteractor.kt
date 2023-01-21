package com.example.dynamicformapp.feature.form.domain

import com.example.dynamicformapp.feature.form.model.FormTextVO
import com.example.dynamicformapp.feature.form.model.FormVO

interface FormInteractor {
    fun getForms(): List<FormVO>
    fun listenFormUpdates(input: (Int) -> Unit, validation: (Boolean) -> Unit)
    fun onReadInput(position: Int, value: String)
    fun onCheckbox(position: Int, value: Boolean)
}

abstract class BaseFormIteractor : FormInteractor {
    protected abstract var inputForms: List<FormVO>
    protected abstract var onValidation: ((Boolean) -> Unit)
    protected abstract var onNotifyUpdateAt: ((Int) -> Unit)
    protected abstract fun performValidation()

    override fun getForms(): List<FormVO> = inputForms

    fun onReadInput(
        position: Int,
        value: String,
        error: String?
    ) {
        performValidation()
        inputForms[position].let {
//            it.inputError = error
//            it.inputText = value
        }
        onNotifyUpdateAt.invoke(position)
    }

    override fun onCheckbox(position: Int, value: Boolean) {
//        inputForms[position].checkBox?.onInputValidation?.invoke(position, value)
    }

    override fun listenFormUpdates(input: (Int) -> Unit, validation: (Boolean) -> Unit) {
        onNotifyUpdateAt = input
        onValidation = validation
    }

    override fun onReadInput(position: Int, value: String) {
//        inputForms[position].onInputValidation.invoke(position, value)
    }
}