package com.example.dynamicformapp.feature.form.domain

import com.example.dynamicformapp.core.domain.BaseUseCase
import com.example.dynamicformapp.feature.form.domain.model.FormIO
import com.example.dynamicformapp.feature.form.domain.model.FormRule
import com.example.dynamicformapp.feature.form.domain.model.FormTextVO
import com.example.dynamicformapp.feature.form.domain.model.FormRuleSet

typealias IO = ((input: FormIO) -> Unit)
typealias OutputListener = ((value: String, isSelected: Boolean, ruleSet: FormRuleSet?) -> Unit)

interface FormUsaCaseInput {
    fun onInput(input: FormIO)
    fun onValidation(output: OutputListener) {}
}

abstract class FormUsaCase<VO> : FormUsaCaseInput, BaseUseCase<IO, VO>() {

    private lateinit var outputListener: IO

    protected open val ruleSet: FormRuleSet? = null
    protected var ruleSetListener: OutputListener? = null

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
        (formVO as FormTextVO).let { vo ->
            doTextValidation(vo, input)
            vo.ruleSet?.let { set ->
                set.hasErrors = !isValid
                set.onRuleCallback.invoke(set)
            }
        }
    }

    private fun selectionInput(input: FormIO) {
        isValid = input.isSelected
    }

    private fun doTextValidation(vo: FormTextVO, input: FormIO) {
        vo.text = input.value
        isValid = vo.ruleSet?.rules?.let { rules ->
            verifyRuleSet(vo.text, rules) { error ->
                input.error = error
            }
        } ?: (input.value.length <= vo.maxSize && input.value.length >= vo.minSize)
    }

    protected fun onRuleSetValidations(vo: FormTextVO) {
        isValid = vo.ruleSet?.rules?.let { verifyRuleSet(vo.text, it) } ?: false
        ruleSetListener?.invoke(vo.text, isValid, vo.ruleSet)
    }

    private fun verifyRuleSet(
        value: String,
        rules: List<FormRule>,
        onValidation: ((error: String?) -> Unit)? = null
    ): Boolean {
        with(rules) {
            map { rule ->
                value.contains(rule.regex).let { isValid ->
                    rule.isValid = isValid
                    if (isValid) {
                        onValidation?.invoke(null)
                    } else {
                        onValidation?.invoke(rule.error)
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
