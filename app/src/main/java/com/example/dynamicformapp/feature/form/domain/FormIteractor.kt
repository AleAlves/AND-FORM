package com.example.dynamicformapp.feature.form.domain

import com.example.dynamicformapp.feature.form.model.*

interface FormInteractor {
    fun getForms(): List<FormVO>
    fun onInput(input: FormData)
    fun onOutput(input: FormData)
    suspend fun listenFormUpdates(
        notifyAt: (Int) -> Unit,
        validation: (Boolean) -> Unit
    )
}

abstract class FormIteractorImpl : FormInteractor {
    protected abstract var inputForms: ArrayList<FormVO>
    protected var onValidate: ((Boolean) -> Unit) = {}
    private var onNotifyOutputAt: ((Int) -> Unit) = {}
    protected abstract fun performValidation()

    override fun getForms(): List<FormVO> = inputForms

    override fun onInput(input: FormData) {
        inputForms[input.position].onInput.invoke(input)
    }

    override fun onOutput(input: FormData) {
        performValidation()
        inputForms[input.position].let {
            when (it) {
                is FormTextVO -> applyTextChange(it, input)
                is FormRadioVO -> applyRadioChange(it, input)
                is FormCheckVO -> applyCheckChange(it, input)
            }
        }
    }

    override suspend fun listenFormUpdates(
        notifyAt: (Int) -> Unit,
        validation: (Boolean) -> Unit
    ) {
        onNotifyOutputAt = notifyAt
        onValidate = validation
    }

    private fun applyTextChange(formVO: FormTextVO, input: FormData) {
        formVO.error = input.error
        formVO.text = input.value
        formVO.checkBox?.isSelected = input.isSelected
        onNotifyOutputAt.invoke(input.position)
    }

    private fun applyCheckChange(formVO: FormCheckVO, input: FormData) {
        formVO.isSelected = input.isSelected
        onNotifyOutputAt.invoke(input.position)
    }

    private fun applyRadioChange(formVO: FormRadioVO, input: FormData) {
        inputForms.mapIndexed { index, vo ->
            if (vo is FormRadioVO) {
                vo.isSelected = vo == formVO && input.isSelected
                onNotifyOutputAt.invoke(index)
            }
        }
    }

}