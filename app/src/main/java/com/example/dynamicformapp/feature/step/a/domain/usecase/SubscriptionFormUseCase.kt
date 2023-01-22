package com.example.dynamicformapp.feature.step.a.domain.usecase

import com.example.dynamicformapp.core.domain.BaseUseCase
import com.example.dynamicformapp.core.domain.UseCaseInput
import com.example.dynamicformapp.feature.form.model.FormInput
import com.example.dynamicformapp.feature.form.model.FormRadioVO
import javax.inject.Inject

class SubscriptionFormUseCase @Inject constructor() : BaseUseCase<List<FormRadioVO>>() {

    override fun invoke(input: UseCaseInput): List<FormRadioVO> {
        inputListener = input
        return listOf(
            FormRadioVO(
                id = "abc",
                text = "I want to receive news and offers",
                isSelected = false,
                ::onReadInput
            ),
            FormRadioVO(
                id = "abc",
                text = "Just essential information",
                isSelected = false,
                ::onReadInput
            )
        )
    }

    override fun onReadInput(input: FormInput) {
        inputListener.invoke(input)
    }

}