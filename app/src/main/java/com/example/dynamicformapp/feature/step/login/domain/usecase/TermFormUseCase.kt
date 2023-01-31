package com.example.dynamicformapp.feature.step.login.domain.usecase

import com.example.dynamicformapp.feature.form.domain.FormUsaCase
import com.example.dynamicformapp.feature.form.domain.model.FormCheckVO
import com.example.dynamicformapp.feature.form.domain.model.FormValidation
import javax.inject.Inject

class TermFormUseCase @Inject constructor() : FormUsaCase<FormCheckVO>() {

    override val vo: FormCheckVO = FormCheckVO(
        text = "I accept the terms and stuff",
        isSelected = false,
        isEnabled = true,
        onInput = ::onReadInput
    )
}