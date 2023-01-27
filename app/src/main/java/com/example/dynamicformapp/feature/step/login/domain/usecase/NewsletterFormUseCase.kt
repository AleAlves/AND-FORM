package com.example.dynamicformapp.feature.step.login.domain.usecase

import com.example.dynamicformapp.core.domain.BaseUseCase
import com.example.dynamicformapp.core.domain.UseCaseInput
import com.example.dynamicformapp.feature.form.model.FormCheckVO
import com.example.dynamicformapp.feature.form.model.FormInput
import com.example.dynamicformapp.feature.form.model.FormRadioVO
import java.util.UUID
import javax.inject.Inject

class NewsletterFormUseCase @Inject constructor() : BaseUseCase<List<FormRadioVO>>() {

    var newsLetterChoice = ""

    override val formVO: List<FormRadioVO> = listOf(
        FormRadioVO(
            id = "yes123",
            text = "Yes, I want to reveice all news and offers",
            isSelected = false,
            onInput = ::onReadInput
        ),
        FormRadioVO(
            id = "no456",
            text = "No, only important communication",
            isSelected = false,
            onInput = ::onReadInput
        )
    )

    override fun invoke(input: UseCaseInput): List<FormRadioVO> {
        inputListener = input
        return formVO
    }

    override fun onReadInput(input: FormInput) {
        isValid = input.isSelected
        newsLetterChoice = input.value
        inputListener.invoke(input)
    }

}