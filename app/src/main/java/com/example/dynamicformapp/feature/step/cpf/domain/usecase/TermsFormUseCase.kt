package com.example.dynamicformapp.feature.step.cpf.domain.usecase

import com.example.dynamicformapp.feature.form.domain.FormUsaCase
import com.example.dynamicformapp.feature.form.domain.model.FormCheckVO
import javax.inject.Inject

class TermsFormUseCase @Inject constructor() : FormUsaCase<FormCheckVO>() {

    override val formVO: FormCheckVO = FormCheckVO(
        text = "I accept the terms and stuff",
        onInput = ::onInput
    )
}