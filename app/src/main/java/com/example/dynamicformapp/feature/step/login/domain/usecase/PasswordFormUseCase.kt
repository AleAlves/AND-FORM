package com.example.dynamicformapp.feature.step.login.domain.usecase

import android.text.InputType
import com.example.dynamicformapp.feature.form.domain.FormRules
import com.example.dynamicformapp.feature.form.domain.FormUsaCase
import com.example.dynamicformapp.feature.form.domain.model.FormCheckVO
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
                name = "The password size must be 6",
            ),
            FormRule(
                regex = Regex(".*[0-9].*"),
                name = "The password must constains numbers",
            ),
            FormRule(
                regex = Regex(".*[a-zA-Z].*"),
                name = "The password must constain letter",
            ),
            FormRule(
                regex = Regex("[\$&+,:;=?@#|'<>.^*()%!-]"),
                name = "The password must constain special character",
            )
        ),
        onRuleCallback = ::onRuleValidation
    )

    override val formVO: FormTextVO = FormTextVO(
        hint = "Password",
        maxSize = 6,
        minSize = 6,
        checkBox = FormCheckVO(
            text = "Remember me",
            onInput = ::onInput
        ),
        ruleSet = ruleSet,
        isSingleLine = true,
        requestFocus = false,
        inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD,
        onInput = ::onInput
    )

    override fun onValidation(rules: FormRules) {
        rulesListener = rules
        runRulesValidations(formVO)
    }
}