package com.example.dynamicformapp.feature.step.login.domain.usecase

import com.example.dynamicformapp.core.domain.BaseUseCase
import com.example.dynamicformapp.core.domain.UseCaseInput
import com.example.dynamicformapp.feature.form.model.FormCheckVO
import com.example.dynamicformapp.feature.form.model.FormInput
import javax.inject.Inject

class TermFormUseCase @Inject constructor() : BaseUseCase<FormCheckVO>() {

    override val formVO: FormCheckVO = FormCheckVO(
        text = "I accept the terms and stuff",
        isSelected = false,
        ::onReadInput
    )

    override fun invoke(input: UseCaseInput): FormCheckVO {
        inputListener = input
        return formVO
    }

    override fun onReadInput(input: FormInput) {
        isValid = input.isSelected
        inputListener.invoke(input)
    }

}