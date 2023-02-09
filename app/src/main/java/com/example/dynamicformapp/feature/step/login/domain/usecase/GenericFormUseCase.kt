package com.example.dynamicformapp.feature.step.login.domain.usecase

import android.text.InputType
import com.example.dynamicformapp.feature.form.domain.FormRules
import com.example.dynamicformapp.feature.form.domain.FormUsaCase
import com.example.dynamicformapp.feature.form.domain.model.FormRule
import com.example.dynamicformapp.feature.form.domain.model.FormTextVO
import com.example.dynamicformapp.feature.form.domain.model.FormRuleSet
import javax.inject.Inject

class GenericFormUseCase @Inject constructor() : FormUsaCase<FormTextVO>() {

    override var ruleSet: FormRuleSet = FormRuleSet(
        rules = listOf(),
        onRuleCallback = ::onRuleValidation
    )

    override val formVO: FormTextVO = FormTextVO(
        text = "",
        maxSize = 100,
        minSize = 0,
        requestFocus = false,
        isSingleLine = true,
        isEnabled = false,
        isReadOnly = true,
        hasCounter = false,
        gridSpan = 1,
        ruleSet = ruleSet,
        inputType = InputType.TYPE_CLASS_TEXT,
        onInput = ::onInput
    )

    override fun onValidation(rules: FormRules) {
        rulesListener = rules
        runRulesValidations(formVO)
    }
}