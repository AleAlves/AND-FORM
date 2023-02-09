package com.example.dynamicformapp.feature.step.login.domain.usecase

import com.example.dynamicformapp.feature.form.domain.RulesListener
import com.example.dynamicformapp.feature.form.domain.FormUsaCase
import com.example.dynamicformapp.feature.form.domain.model.FormRadioVO
import javax.inject.Inject

class NewsletterFormUseCase @Inject constructor() : FormUsaCase<Array<FormRadioVO>>() {

    override val formVO: Array<FormRadioVO> = arrayOf(
        FormRadioVO(
            id = "yes123",
            text = "Yes, I want to reveice all news and offers",
            onInput = ::onInput
        ),
        FormRadioVO(
            id = "no456",
            text = "No, only important communication",
            onInput = ::onInput
        ),
        FormRadioVO(
            id = "never789",
            text = "No communication",
            isEnabled = false,
            onInput = ::onInput
        )
    )

    override fun onValidation(rules: RulesListener) {
        ruleSetListener = rules
    }
}