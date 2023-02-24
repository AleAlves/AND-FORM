package com.example.dynamicformapp.feature.step.cpf.domain.usecase

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

class CpfFormUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) : FormUsaCase<FormTextVO>() {

    override var ruleSet: FormRuleSet = FormRuleSet(
        rules = listOf(
            FormRule(
                regex = Regex("^.{14}\$")
            )
        ),
        onRuleCallback = ::onRuleSetValidation
    )

    override val formVO: FormTextVO = FormTextVO(
        hint = context.getString(R.string.cpf_input_hint),
        helper = context.getString(R.string.cpf_input_helper),
        maxSize = 14,
        minSize = 14,
        requestFocus = true,
        isSingleLine = true,
        ruleSet = ruleSet,
        mask = "###.###.###-##",
        inputType = InputType.TYPE_CLASS_NUMBER,
        onInput = ::onInput,
    )
}