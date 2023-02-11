package com.example.dynamicformapp.feature.step.name.domain

import android.content.Context
import android.text.InputType
import com.example.dynamicformapp.R
import com.example.dynamicformapp.feature.form.domain.RulesListener
import com.example.dynamicformapp.feature.form.domain.FormUsaCase
import com.example.dynamicformapp.feature.form.domain.model.FormCheckVO
import com.example.dynamicformapp.feature.form.domain.model.FormRule
import com.example.dynamicformapp.feature.form.domain.model.FormTextVO
import com.example.dynamicformapp.feature.form.domain.model.FormRuleSet
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NameFormUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) : FormUsaCase<FormTextVO>() {

    override val formVO: FormTextVO = FormTextVO(
        hint = context.getString(R.string.address_name_input_hint),
        subtitle = context.getString(R.string.address_name_input_helper),
        maxSize = 100,
        minSize = 3,
        requestFocus = true,
        isSingleLine = true,
        isEnabled = true,
        inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME,
        onInput = ::onInput,
    )

    override fun onValidation(rules: RulesListener) {
        ruleSetListener = rules
        onRuleSetValidations(formVO)
    }
}