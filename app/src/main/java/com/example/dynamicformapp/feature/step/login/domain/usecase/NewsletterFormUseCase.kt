package com.example.dynamicformapp.feature.step.login.domain.usecase

import com.example.dynamicformapp.feature.form.domain.BaseFormUsaCase
import com.example.dynamicformapp.feature.form.domain.FormInput
import com.example.dynamicformapp.feature.form.model.FormData
import com.example.dynamicformapp.feature.form.model.FormRadioVO
import javax.inject.Inject

class NewsletterFormUseCase @Inject constructor() : BaseFormUsaCase<List<FormRadioVO>>() {

    var newsLetterChoice = ""

    override val formVO: List<FormRadioVO> = listOf(
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

    override fun invoke(input: FormInput): List<FormRadioVO> {
        inputListener = input
        return formVO
    }

    override fun onReadInput(input: FormData) {
        isValid = input.isSelected
        newsLetterChoice = input.value
        inputListener.invoke(input)
    }

}