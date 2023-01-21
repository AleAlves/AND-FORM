package com.example.dynamicformapp.feature.step.a.domain

import com.example.dynamicformapp.core.domain.BaseUseCase
import com.example.dynamicformapp.core.domain.Input
import com.example.dynamicformapp.feature.form.model.FormCheckVO
import com.example.dynamicformapp.feature.form.model.FormTextVO
import javax.inject.Inject

class TermFormUseCase @Inject constructor() : BaseUseCase<FormCheckVO>() {

    var accpected = false

    override fun invoke(validation: Input): FormCheckVO {
        inputListener = validation
        return FormCheckVO(
            caption = "I accept the terms and stuff",
            isChecked = false,
            ::onCheckBoxValidation
        )
    }

    override fun onInputValidation(position: Int, value: String) {}

    override fun onCheckBoxValidation(position: Int, value: Boolean) {
        accpected = value
    }
}