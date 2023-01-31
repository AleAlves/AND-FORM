package com.example.dynamicformapp.feature.step.login.domain.usecase

import android.content.Context
import android.text.InputType
import com.example.dynamicformapp.R
import com.example.dynamicformapp.feature.form.domain.FormRules
import com.example.dynamicformapp.feature.form.domain.FormUsaCase
import com.example.dynamicformapp.feature.form.model.FormRuleSet
import com.example.dynamicformapp.feature.form.model.FormTextVO
import com.example.dynamicformapp.feature.form.model.FormValidation
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class EmailFormUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) : FormUsaCase<FormTextVO>() {

    override var isValid: Boolean = false

    override val rules: FormValidation = FormValidation(
        rules = listOf(
            FormRuleSet(
                regex = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
            )
        ),
        onRuleCallback = ::onRules
    )

    override val vo: FormTextVO = FormTextVO(
        hint = context.getString(R.string.login_email_input_hint),
        subtitle = context.getString(R.string.login_email_input_helper),
        maxSize = 50,
        minSize = 5,
        requestFocus = true,
        isEnabled = true,
        isSingleLine = true,
        onInput = ::onReadInput,
        validation = rules,
        inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
    )

    override fun onRulesValidations(rules: FormRules) {
        rulesListener = rules
    }

    override fun onRules(rules: FormValidation) {
        isValid = rules.hasErrors.not()
    }
}