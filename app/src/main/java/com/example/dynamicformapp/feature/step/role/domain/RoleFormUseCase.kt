package com.example.dynamicformapp.feature.step.role.domain

import com.example.dynamicformapp.feature.form.domain.RulesListener
import com.example.dynamicformapp.feature.form.domain.FormUsaCase
import com.example.dynamicformapp.feature.form.domain.model.FormRadioVO
import javax.inject.Inject

class RoleFormUseCase @Inject constructor() : FormUsaCase<Array<FormRadioVO>>() {

    override val formVO: Array<FormRadioVO> = arrayOf(
        FormRadioVO(
            id = "123",
            text = "Personal",
            onInput = ::onInput
        ),
        FormRadioVO(
            id = "456",
            text = "Professional",
            onInput = ::onInput
        ),
        FormRadioVO(
            id = "789",
            text = "Student",
            onInput = ::onInput
        ),
        FormRadioVO(
            id = "012",
            text = "Other",
            onInput = ::onInput
        ),
    )

    override fun onValidation(rules: RulesListener) {
        ruleSetListener = rules
    }
}