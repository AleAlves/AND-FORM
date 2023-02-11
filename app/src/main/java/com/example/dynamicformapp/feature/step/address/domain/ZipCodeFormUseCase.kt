package com.example.dynamicformapp.feature.step.address.domain

import android.content.Context
import android.text.InputType
import com.example.dynamicformapp.R
import com.example.dynamicformapp.feature.form.domain.OutputListener
import com.example.dynamicformapp.feature.form.domain.FormUsaCase
import com.example.dynamicformapp.feature.form.domain.model.FormRule
import com.example.dynamicformapp.feature.form.domain.model.FormTextVO
import com.example.dynamicformapp.feature.form.domain.model.FormRuleSet
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ZipCodeFormUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) : FormUsaCase<FormTextVO>() {

    override var ruleSet: FormRuleSet = FormRuleSet(
        rules = listOf(
            FormRule(
                regex = Regex("^.{9}\$")
            )
        ),
        onRuleCallback = ::onRuleSetValidation
    )

    override val formVO: FormTextVO = FormTextVO(
        hint = context.getString(R.string.address_zip_input_hint),
        subtitle = context.getString(R.string.address_zip_input_helper),
        maxSize = 9,
        minSize = 9,
        requestFocus = true,
        isSingleLine = true,
        ruleSet = ruleSet,
        mask = "#####-###",
        inputType = InputType.TYPE_CLASS_NUMBER,
        onInput = ::onInput,
    )

    override fun onValidation(output: OutputListener) {
        ruleSetListener = output
        onRuleSetValidations(formVO)
    }
}