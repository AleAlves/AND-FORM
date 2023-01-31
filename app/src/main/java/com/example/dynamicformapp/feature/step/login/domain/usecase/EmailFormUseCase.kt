package com.example.dynamicformapp.feature.step.login.domain.usecase

import android.content.Context
import android.text.InputType
import com.example.dynamicformapp.R
import com.example.dynamicformapp.feature.form.domain.FormRules
import com.example.dynamicformapp.feature.form.domain.FormUsaCase
import com.example.dynamicformapp.feature.form.domain.model.FormRuleSet
import com.example.dynamicformapp.feature.form.domain.model.FormTextVO
import com.example.dynamicformapp.feature.form.domain.model.FormValidation
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class EmailFormUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) : FormUsaCase<FormTextVO>() {

    override var rules: FormValidation = FormValidation(
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
        validation = rules,
        inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS,
        onInput = ::onInput
    )

    override fun onValidation(rules: FormRules) {
        rulesListener = rules
    }
}