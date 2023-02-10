package com.example.dynamicformapp.feature.form.domain

import com.example.dynamicformapp.core.domain.BaseUseCase
import com.example.dynamicformapp.feature.form.domain.model.FormIO
import com.example.dynamicformapp.feature.form.domain.model.FormTextVO
import com.example.dynamicformapp.feature.form.domain.model.FormRuleSet

typealias IO = ((input: FormIO) -> Unit)
typealias RulesListener = ((value: String, isSelected: Boolean, ruleSet: FormRuleSet?) -> Unit)

interface FormUsaCaseInput {
    fun onInput(input: FormIO)
    fun onValidation(rules: RulesListener) {}
}

abstract class FormUsaCase<VO> : FormUsaCaseInput, BaseUseCase<IO, VO>() {

    private lateinit var outputListener: IO

    protected open val ruleSet: FormRuleSet? = null
    protected var ruleSetListener: RulesListener? = null

    abstract val formVO: VO
    var isValid: Boolean = false

    override fun invoke(output: IO): VO {
        outputListener = output
        return formVO
    }

    override fun onInput(input: FormIO) {
        when (formVO) {
            is FormTextVO -> textInput(input)
            else -> selectionInput(input)
        }
        ruleSetListener?.invoke(input.value, input.isSelected, ruleSet)
        outputListener.invoke(input)
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
        }
    }

    private fun selectionInput(input: FormIO) {
        isValid = input.isSelected
    }

    private fun doTextValidation(vo: FormTextVO, input: FormIO) {
        vo.text = input.value
        isValid = verifyRuleSet(vo) { error ->
            input.error = error
        }
    }

    protected fun onRuleSetValidations(vo: FormTextVO) {
        isValid = verifyRuleSet(vo)
        ruleSetListener?.invoke(vo.text, isValid, vo.ruleSet)
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
            return this.none { rule -> rule.isValid.not() }
        }
    }

    fun onRuleSetValidation(rules: FormRuleSet) {
        isValid = rules.hasErrors.not()
    }
}
