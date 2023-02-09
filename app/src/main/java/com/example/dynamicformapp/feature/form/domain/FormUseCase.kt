package com.example.dynamicformapp.feature.form.domain

import com.example.dynamicformapp.core.domain.BaseUseCase
import com.example.dynamicformapp.feature.form.domain.model.FormIO
import com.example.dynamicformapp.feature.form.domain.model.FormTextVO
import com.example.dynamicformapp.feature.form.domain.model.FormRuleSet

typealias FormInput = ((input: FormIO) -> Unit)
typealias FormRules = ((value: String, isSelected: Boolean, ruleSet: FormRuleSet?) -> Unit)

interface FormUsaCaseInput {
    fun onInput(input: FormIO)
    fun onValidation(rules: FormRules) {}
}

abstract class FormUsaCase<T> : FormUsaCaseInput, BaseUseCase<T, FormInput>() {

    private var inputListener: FormInput = { _ -> }

    protected open val ruleSet: FormRuleSet? = null
    protected var rulesListener: FormRules = { _, _, _ -> }

    abstract val formVO: T
    var isValid: Boolean = false

    override fun invoke(input: FormInput): T {
        inputListener = input
        return formVO
    }

    override fun onInput(input: FormIO) {
        when (formVO) {
            is FormTextVO -> textInput(input)
            else -> selectionInput(input)
        }
        inputListener.invoke(input)
    }

    private fun textInput(input: FormIO) {
        (formVO as FormTextVO).let {
            if (input.value.length > it.maxSize && input.value.length < it.minSize) {
                isValid = false
            } else {
                doTextValidation(it, input)
                with(it.ruleSet) {
                    hasErrors = !isValid
                    onRuleCallback.invoke(this)
                }
            }
            rulesListener.invoke(input.value, input.isSelected, ruleSet)
        }
    }

    private fun selectionInput(input: FormIO) {
        isValid = input.isSelected
        rulesListener.invoke(input.value, input.isSelected, ruleSet)
    }

    private fun doTextValidation(vo: FormTextVO, input: FormIO) {
        vo.text = input.value
        isValid = verifyRuleSet(vo) { error ->
            input.error = error
        }
    }

    protected fun runRulesValidations(vo: FormTextVO) {
        isValid = verifyRuleSet(vo)
        rulesListener.invoke(vo.text, isValid, vo.ruleSet)
    }

    private fun verifyRuleSet(
        vo: FormTextVO,
        onValidation: ((error: String?) -> Unit)? = null
    ): Boolean {
        with(vo.ruleSet.rules) {
            map { rule ->
                vo.text.contains(rule.regex).let {
                    rule.isValid = it
                    if (it) {
                        onValidation?.invoke(rule.error)
                    } else {
                        onValidation?.invoke(null)
                    }
                }
            }
            return this.none { rule -> !rule.isValid }
        }
    }

    fun onRuleValidation(rules: FormRuleSet) {
        isValid = rules.hasErrors.not()
    }
}
