package com.example.dynamicformapp.feature.step.a.domain.usecase

import com.example.dynamicformapp.core.domain.BaseUseCase
import com.example.dynamicformapp.core.domain.UseCaseInput
import com.example.dynamicformapp.feature.form.model.FormCheckVO
import com.example.dynamicformapp.feature.form.model.FormInput
import javax.inject.Inject

class TermFormUseCase @Inject constructor() : BaseUseCase<FormCheckVO>() {

    var accpected = false

    override fun invoke(input: UseCaseInput): FormCheckVO {
        inputListener = input
        return FormCheckVO(
            text = "I accept the terms and stuff",
            isSelected = false,
            ::onReadInput
        )
    }

    override fun onReadInput(input: FormInput) {
        accpected = input.isSelected
        inputListener.invoke(input)
    }
}