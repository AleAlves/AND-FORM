package com.example.dynamicformapp.feature.step.login.domain.usecase

import android.text.InputType
import com.example.dynamicformapp.feature.form.domain.FormRules
import com.example.dynamicformapp.feature.form.domain.FormUsaCase
import com.example.dynamicformapp.feature.form.domain.model.*
import javax.inject.Inject

class AddressUseCase @Inject constructor() : FormUsaCase<FormTextVO>() {

    override var rules: FormValidation = FormValidation(
        rules = listOf(
            FormRule(
                regex = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
            )
        ),
        onRuleCallback = ::onRules
    )

    override val vo: FormTextVO = FormTextVO(
        text = "São Paulo",
        maxSize = 50,
        minSize = 5,
        requestFocus = true,
        isSingleLine = true,
        validation = rules,
        inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS,
        isReadOnly = true,
        onInput = ::onInput
    )


    override fun onValidation(rules: FormRules) {
        rulesListener = rules
    }
}