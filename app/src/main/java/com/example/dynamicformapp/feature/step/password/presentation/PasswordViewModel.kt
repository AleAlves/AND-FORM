package com.example.dynamicformapp.feature.step.password.presentation

import com.example.dynamicformapp.feature.form.domain.model.FormRuleSet
import com.example.dynamicformapp.feature.form.presentation.FormViewModel
import com.example.dynamicformapp.feature.step.password.domain.PasswordFormUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val passwordFormUseCase: PasswordFormUseCase,
    private val passwordConfirmFormUseCase: PasswordFormUseCase,
) : FormViewModel() {

    private var password = ""
    private var passwordCounterPart = ""

    private val passWordRulesLiveData = intoMediator<PasswordState>()

    override fun onLoadForms() {
        initForms(
            passwordFormUseCase(::onOutput),
            passwordConfirmFormUseCase(::onOutput)
        )
    }

    override fun onSetupForms() {
        passwordConfirmFormUseCase.formVO.hint = "Confirm password"
        passwordConfirmFormUseCase.formVO.requestFocus = false
    }

    override fun onValidations() {
        passwordFormUseCase.onValidation {
            password = it.value
            launch {
                passWordRulesLiveData.postValue(PasswordState.LoadRules(rules = it.ruleSet))
            }
        }
        passwordConfirmFormUseCase.onValidation {
            passwordCounterPart = it.value
        }
    }

    override fun getValidations(): Boolean =
        passwordFormUseCase.isValid() && password == passwordCounterPart

    sealed class PasswordState : FormState() {
        data class LoadRules(val rules: FormRuleSet?) : PasswordState()
    }
}