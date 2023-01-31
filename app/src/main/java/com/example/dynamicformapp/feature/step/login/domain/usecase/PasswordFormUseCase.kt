package com.example.dynamicformapp.feature.step.login.domain.usecase

import android.text.InputType
import com.example.dynamicformapp.feature.form.domain.FormRules
import com.example.dynamicformapp.feature.form.domain.FormUsaCase
import com.example.dynamicformapp.feature.form.model.*
import javax.inject.Inject

class PasswordFormUseCase @Inject constructor() :
    FormUsaCase<FormTextVO>() {

    override var isValid: Boolean = false

    override val rules: FormValidation = FormValidation(
        rules = listOf(
            FormRuleSet(
                regex = Regex(".*[0-9].*"),
                text = "The password must constains numbers",
                error = "numbers needed"
            ),
            FormRuleSet(
                regex = Regex(".*[a-zA-Z].*"),
                text = "The password must constain letter",
                error = "letters needed"
            ),
            FormRuleSet(
                regex = Regex("[\$&+,:;=?@#|'<>.^*()%!-]"),
                text = "The password must constain special character",
                error = "chars needed"
            )
        ),
        onRuleCallback = ::onRules
    )

    override val vo: FormTextVO = FormTextVO(
        hint = "Password",
        maxSize = 15,
        minSize = 6,
        checkBox = FormCheckVO(
            text = "Remember me",
            isSelected = false,
            isEnabled = true,
            onInput = ::onReadSelectionInput
        ),
        validation = rules,
        isEnabled = true,
        isSingleLine = true,
        requestFocus = false,
        onInput = ::onReadInput,
        inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
    )

    override fun onRules(rules: FormValidation) {
        isValid = rules.hasErrors.not()
    }

    override fun onRulesValidations(rules: FormRules) {
        rulesListener = rules
    }
}