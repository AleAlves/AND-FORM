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

    override val initialState: FormState = FormState.Init

    override fun loadForms() {
        initForms(
            passwordFormUseCase(::onOutput),
            passwordConfirmFormUseCase(::onOutput)
        )
    }

    override fun onSetupForms() {
        super.onSetupForms()
        passwordConfirmFormUseCase.formVO.hint = "Confirm password"
    }

    override fun setupValidations() {
        passwordFormUseCase.onValidation { value, _, rules ->
            password = value
            setViewState(PasswordState.LoadRules(rules = rules))
        }
        passwordConfirmFormUseCase.onValidation { value, _, _ ->
            passwordCounterPart = value
        }
    }

    override fun getValidations(): Boolean =
        passwordFormUseCase.isValid && password == passwordCounterPart

    sealed class PasswordState : FormState() {
        data class LoadRules(val rules: FormRuleSet?) : PasswordState()
    }
}