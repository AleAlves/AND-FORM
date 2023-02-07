package com.example.dynamicformapp.feature.form.domain

import com.example.dynamicformapp.core.domain.BaseUseCase
import com.example.dynamicformapp.feature.form.domain.model.FormIO
import com.example.dynamicformapp.feature.form.domain.model.FormTextVO
import com.example.dynamicformapp.feature.form.domain.model.FormValidation

typealias FormInput = ((FormIO) -> Unit)
typealias FormRules = ((String, Boolean, FormValidation?) -> Unit)

interface FormUsaCaseInput {
    fun onInput(input: FormIO)
    fun onValidation(rules: FormRules) {}
    fun runValidations() {}
}

abstract class FormUsaCase<T> : FormUsaCaseInput, BaseUseCase<T, FormInput>() {

    protected open val rules: FormValidation? = null
    protected var rulesListener: FormRules = { _, _, _ -> }
    private var inputListener: FormInput = { _ -> }
    abstract val vo: T
    var isValid: Boolean = false

    override fun invoke(input: FormInput): T {
        inputListener = input
        return vo
    }

    override fun onInput(input: FormIO) {
        when (vo) {
            is FormTextVO -> textInput(input)
            else -> selectionInput(input)
        }
        inputListener.invoke(input)
    }

    private fun textInput(input: FormIO) {
        (vo as FormTextVO).let {
            if (input.value.length > it.maxSize && input.value.length < it.minSize) {
                isValid = false
            } else {
                doTextValidation(it, input)
                with(it.validation) {
                    hasErrors = !isValid
                    onRuleCallback.invoke(this)
                }
            }
            rulesListener.invoke(input.value, input.isSelected, rules)
        }
    }

    private fun doTextValidation(vo: FormTextVO, input: FormIO) {
        with(vo.validation.rules) {
            map { rule ->
                if (input.value.contains(rule.regex)) {
                    rule.isValid = true
                    input.error = null
                } else {
                    input.error = rule.error
                    rule.isValid = false
                }
            }
            isValid = this.none { rule -> !rule.isValid }
        }
    }

    private fun selectionInput(input: FormIO) {
        isValid = input.isSelected
        rulesListener.invoke(input.value, input.isSelected, rules)
    }

    protected fun runRulesValidations(vo: FormTextVO) {
        with(vo.validation.rules) {
            map { rule ->
                rule.isValid = vo.text.contains(rule.regex)
            }
            isValid = this.none { rule -> !rule.isValid }
        }
        rulesListener.invoke(vo.text, isValid, vo.validation)
    }

    fun onRuleValidation(rules: FormValidation) {
        isValid = rules.hasErrors.not()
    }
}
