package com.example.dynamicformapp.feature.step.login.presentation

import android.util.Log
import com.example.dynamicformapp.feature.form.domain.model.FormRule
import com.example.dynamicformapp.feature.form.presentation.FormViewModel
import com.example.dynamicformapp.feature.step.login.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val emailFormUseCase: EmailFormUseCase,
    private val passwordFormUseCase: PasswordFormUseCase,
    private val termFormUseCase: TermFormUseCase,
    private val newsletterFormUseCase: NewsletterFormUseCase,
    private val addressUseCase: AddressUseCase,
    private val stateUseCase: StateUseCase,
) : FormViewModel() {

    private var email = ""
    private var password = ""
    private var newsletter = ""
    private var rememberPassword = false

    init {
        initForms(
            addressUseCase(::onOutput),
            stateUseCase(::onOutput),
            emailFormUseCase(::onOutput),
            passwordFormUseCase(::onOutput),
            termFormUseCase(::onOutput),
            *newsletterFormUseCase(::onOutput)
        )
        loadSaved()
    }

    private fun loadSaved() {
        emailFormUseCase.vo.text = "wow@email.com"
    }

    override fun setupValidations() {
        emailFormUseCase.onValidation { input, _ ->
            email = input.value
        }
        newsletterFormUseCase.onValidation { input, _ ->
            newsletter = input.value
        }
        passwordFormUseCase.onValidation { input, rules ->
            password = input.value
            rememberPassword = input.isSelected
            setRules(rules?.rules)
        }
    }

    override fun getValidations(): Boolean =
        emailFormUseCase.isValid
            .and(passwordFormUseCase.isValid)
            .and(termFormUseCase.isValid)

    override val initialState: FormState = FormState.Init

    private fun setRules(validations: List<FormRule>?) {
        setViewState(LoginState.OnLoadPasswordRules(validations))
    }

    fun doLogin() {
        Log.d("WOW", "Mail: $email Password: $password Newsletter: $newsletter")
    }

    sealed class LoginState : FormState() {
        data class OnLoadPasswordRules(val rules: List<FormRule>?) : LoginState()
    }
}