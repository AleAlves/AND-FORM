package com.example.dynamicformapp.feature.step.login.domain.usecase

import com.example.dynamicformapp.feature.form.domain.BaseFormUsaCase
import com.example.dynamicformapp.feature.form.domain.FormInput
import com.example.dynamicformapp.feature.form.model.FormCheckVO
import com.example.dynamicformapp.feature.form.model.FormData
import javax.inject.Inject

class TermFormUseCase @Inject constructor() : BaseFormUsaCase<FormCheckVO>() {

    override val formVO: FormCheckVO = FormCheckVO(
        text = "I accept the terms and stuff",
        isSelected = false,
        isEnabled = true,
        onInput = ::onReadInput
    )

    override fun invoke(input: FormInput): FormCheckVO {
        inputListener = input
        return formVO
    }

    override fun onReadInput(input: FormData) {
        isValid = input.isSelected
        inputListener.invoke(input)
    }

}