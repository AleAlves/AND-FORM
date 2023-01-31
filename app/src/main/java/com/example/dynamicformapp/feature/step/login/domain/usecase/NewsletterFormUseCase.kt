package com.example.dynamicformapp.feature.step.login.domain.usecase

import com.example.dynamicformapp.feature.form.domain.FormUsaCase
import com.example.dynamicformapp.feature.form.model.FormRadioVO
import com.example.dynamicformapp.feature.form.model.FormValidation
import javax.inject.Inject

class NewsletterFormUseCase @Inject constructor() : FormUsaCase<List<FormRadioVO>>() {

    override var isValid: Boolean = false

    override val vo: List<FormRadioVO> = listOf(
        FormRadioVO(
            id = "yes123",
            text = "Yes, I want to reveice all news and offers",
            isSelected = false,
            isEnabled = true,
            onInput = ::onReadInput
        ),
        FormRadioVO(
            id = "no456",
            text = "No, only important communication",
            isSelected = false,
            isEnabled = true,
            onInput = ::onReadInput
        ),
        FormRadioVO(
            id = "never789",
            text = "No communication",
            isSelected = false,
            isEnabled = false,
            onInput = ::onReadInput
        )
    )
    override val rules: FormValidation = FormValidation(listOf(), false, {})
}