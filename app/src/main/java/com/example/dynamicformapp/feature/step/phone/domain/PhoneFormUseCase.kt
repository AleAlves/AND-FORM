package com.example.dynamicformapp.feature.step.phone.domain

import android.text.InputType
import com.example.dynamicformapp.feature.form.domain.RulesListener
import com.example.dynamicformapp.feature.form.domain.FormUsaCase
import com.example.dynamicformapp.feature.form.domain.model.FormRule
import com.example.dynamicformapp.feature.form.domain.model.FormTextVO
import com.example.dynamicformapp.feature.form.domain.model.FormRuleSet
import javax.inject.Inject

class PhoneFormUseCase @Inject constructor() : FormUsaCase<FormTextVO>() {

    override val ruleSet: FormRuleSet = FormRuleSet(
        rules = listOf(
            FormRule(
                regex = Regex("^.{11}\$"),
            ), FormRule(
                regex = Regex(".*[0-9].*"),
            )
        ), onRuleCallback = ::onRuleSetValidation
    )

    override val formVO: FormTextVO = FormTextVO(
        hint = "Phone",
        subtitle = "Don`t worry We wont spam you",
        maxSize = 11,
        minSize = 11,
        ruleSet = ruleSet,
        isSingleLine = true,
        requestFocus = false,
        inputType = InputType.TYPE_CLASS_PHONE,
        onInput = ::onInput
    )

    override fun onValidation(rules: RulesListener) {
        ruleSetListener = rules
        onRuleSetValidations(formVO)
    }
}