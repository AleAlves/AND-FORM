package com.example.dynamicformapp.feature.step.login.presentation

import android.util.Log
import com.example.dynamicformapp.feature.form.presentation.FormViewModel
import com.example.dynamicformapp.feature.step.login.domain.usecase.EmailFormUseCase
import com.example.dynamicformapp.feature.step.login.domain.usecase.PasswordFormUseCase
import com.example.dynamicformapp.feature.step.login.domain.usecase.TermFormUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val emailFormUseCase: EmailFormUseCase,
    private val passwordFormUseCase: PasswordFormUseCase,
    private val termFormUseCase: TermFormUseCase
) : FormViewModel() {

    private var email = ""
    private var password = ""

    init {
        initForms(
            arrayListOf(
                emailFormUseCase(::onOutput),
                passwordFormUseCase(::onOutput),
                termFormUseCase(::onOutput)
            )
        )
    }

    override fun setupValidations() {
        emailFormUseCase.onRulesValidations { value, _ ->
            email = value
        }
        passwordFormUseCase.onRulesValidations { value, rules ->
            password = value
            Log.d("WOW", "E:${rules?.hasErrors} ")
            rules?.rules?.map {
                Log.d("WOW", "V:${it.isValid} - ${it.error}")
            }
        }
    }

    override fun getValidations(): Boolean =
        emailFormUseCase.isValid
            .and(passwordFormUseCase.isValid)
            .and(termFormUseCase.isValid)

    fun doLogin() {
        Log.d("WOW", "Mail: $email Password: $password")
    }

    sealed class LoginState : FormState() {
        data class OnPasswordLengthRule(val isDone: Boolean) : LoginState()
        data class OnPasswordCharactersRule(val isDone: Boolean) : LoginState()
    }
}