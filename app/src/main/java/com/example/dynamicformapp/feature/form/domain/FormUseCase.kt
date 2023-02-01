package com.example.dynamicformapp.feature.form.domain

import com.example.dynamicformapp.core.domain.BaseUseCase
import com.example.dynamicformapp.feature.form.domain.model.FormData
import com.example.dynamicformapp.feature.form.domain.model.FormTextVO
import com.example.dynamicformapp.feature.form.domain.model.FormValidation

typealias FormInput = ((FormData) -> Unit)
typealias FormRules = ((FormData, FormValidation?) -> Unit)

interface FormUsaCaseInput {
    fun onInput(input: FormData)
    fun onValidation(rules: FormRules) {}
}

abstract class FormUsaCase<T> : FormUsaCaseInput, BaseUseCase<T, FormInput>() {

    protected open val rules: FormValidation? = null
    protected var rulesListener: FormRules = { _, _ -> }
    private var inputListener: FormInput = { _ -> }
    abstract val vo: T
    var isValid: Boolean = false

    override fun invoke(input: FormInput): T {
        inputListener = input
        return vo
    }

    override fun onInput(input: FormData) {
        when (vo) {
            is FormTextVO -> textInput(input)
            else -> selectionInput(input)
        }
        inputListener.invoke(input)
    }

    private fun textInput(input: FormData) {
        (vo as FormTextVO).let {
            if (input.value.length > it.maxSize && input.value.length < it.minSize) {
                isValid = false
            } else {
                with(it.rules) {
                    validations.map { rule ->
                        if (input.value.contains(rule.regex)) {
                            rule.isValid = true
                            input.error = null
                        } else {
                            input.error = rule.error
                            rule.isValid = false
                        }
                    }
                    isValid = validations.none { rule -> !rule.isValid }
                    hasErrors = !isValid
                    onRuleCallback.invoke(this)
                }
            }
            rulesListener.invoke(input, rules)
        }
    }

    private fun selectionInput(input: FormData) {
        isValid = input.isSelected
        rulesListener.invoke(input, rules)
    }

    fun onRules(rules: FormValidation) {
        isValid = rules.hasErrors.not()
    }
}
