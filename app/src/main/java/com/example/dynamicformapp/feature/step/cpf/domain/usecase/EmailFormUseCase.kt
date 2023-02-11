package com.example.dynamicformapp.feature.step.cpf.domain.usecase

import android.content.Context
import android.text.InputType
import com.example.dynamicformapp.R
import com.example.dynamicformapp.feature.form.domain.RulesListener
import com.example.dynamicformapp.feature.form.domain.FormUsaCase
import com.example.dynamicformapp.feature.form.domain.model.FormRule
import com.example.dynamicformapp.feature.form.domain.model.FormTextVO
import com.example.dynamicformapp.feature.form.domain.model.FormRuleSet
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class EmailFormUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) : FormUsaCase<FormTextVO>() {

    override var ruleSet: FormRuleSet = FormRuleSet(
        rules = listOf(
            FormRule(
                regex = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
            )
        ),
        onRuleCallback = ::onRuleSetValidation
    )

    override val formVO: FormTextVO = FormTextVO(
        hint = context.getString(R.string.login_email_input_hint),
        subtitle = context.getString(R.string.login_email_input_helper),
        maxSize = 50,
        minSize = 5,
        requestFocus = true,
        isSingleLine = true,
        ruleSet = ruleSet,
        inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS,
        onInput = ::onInput,
    )

    override fun onValidation(rules: RulesListener) {
        ruleSetListener = rules
        onRuleSetValidations(formVO)
    }
}