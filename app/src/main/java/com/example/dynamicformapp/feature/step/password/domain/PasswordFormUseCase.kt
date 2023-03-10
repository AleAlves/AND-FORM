package com.example.dynamicformapp.feature.step.password.domain

import android.text.InputType
import com.example.dynamicformapp.feature.form.domain.FormUsaCase
import com.example.dynamicformapp.feature.form.domain.model.FormRule
import com.example.dynamicformapp.feature.form.domain.model.FormTextVO
import com.example.dynamicformapp.feature.form.domain.model.FormRuleSet
import javax.inject.Inject

class PasswordFormUseCase @Inject constructor() :
    FormUsaCase<FormTextVO>() {

    override val ruleSet: FormRuleSet = FormRuleSet(
        rules = listOf(
            FormRule(
                regex = Regex("^.{6}\$"),
                name = "Size must be 6",
            ),
            FormRule(
                regex = Regex(".*[0-9].*"),
                name = "Must contain numbers",
            ),
            FormRule(
                regex = Regex(".*[a-zA-Z].*"),
                name = "Must contain letters",
            ),
            FormRule(
                regex = Regex("[\$&+,:;=?@#|'<>.^*()%!-]"),
                name = "Must contain special character",
            )
        ),
        onRuleCallback = ::onRuleSetValidation
    )

    override val formVO: FormTextVO = FormTextVO(
        hint = "Password",
        maxSize = 6,
        minSize = 6,
        ruleSet = ruleSet,
        isSingleLine = true,
        requestFocus = true,
        inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD,
        onInput = ::onInput
    )
}