package com.example.dynamicformapp.feature.form.domain

import com.example.dynamicformapp.core.domain.BaseUseCase
import com.example.dynamicformapp.core.util.clearMask
import com.example.dynamicformapp.feature.form.domain.model.*

typealias IO = ((input: FormIO) -> Unit)
typealias OutputListener = ((output: FormOutput) -> Unit)

interface FormUsaCaseInput {
    fun onInput(input: FormIO)
    fun onValidation(output: OutputListener) {}
}

abstract class FormUsaCase<VO> : FormUsaCaseInput, BaseUseCase<IO, VO>() {

    private lateinit var outputListener: IO

    protected open val ruleSet: FormRuleSet? = null
    private var ruleSetListener: OutputListener? = null

    abstract val formVO: VO
    private var isValid: Boolean = false

    override fun invoke(output: IO): VO {
        outputListener = output
        return formVO
    }

    override fun onValidation(output: OutputListener) {
        ruleSetListener = output
        if (formVO is FormTextVO) {
            onInitialValidation(formVO as FormTextVO)
        }
    }

    override fun onInput(input: FormIO) {
        when (formVO) {
            is FormTextVO -> textInput(input)
            else -> selectionInput(input)
        }
        outputListener.invoke(input)
        ruleSetListener?.invoke(FormOutput(input.value.clearMask(), input.isSelected, ruleSet))
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
        } ?: hasTextInputValidRange(vo)
    }

    private fun onInitialValidation(vo: FormTextVO) {
        with(vo) {
            ruleSet?.rules?.let {
                isValid = verifyRuleSet(vo.text, it)
            } ?: hasTextInputValidRange(vo)
            ruleSetListener?.invoke(
                FormOutput(
                    vo.text.clearMask(),
                    checkBox?.isSelected ?: false,
                    vo.ruleSet
                )
            )
        }
    }

    private fun verifyRuleSet(
        value: String, rules: List<FormRule>, onValidation: ((error: String?) -> Unit)? = null
    ): Boolean {
        with(rules) {
            map { rule ->
                value.clearMask().contains(rule.regex).let { isValid ->
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

    private fun hasTextInputValidRange(vo: FormTextVO) = with(vo.text.clearMask()) {
        length <= vo.maxSize && length >= vo.minSize
    }

    fun onRuleSetValidation(rules: FormRuleSet) {
        isValid = rules.hasErrors.not()
    }

    fun isValid() = isValid
}
