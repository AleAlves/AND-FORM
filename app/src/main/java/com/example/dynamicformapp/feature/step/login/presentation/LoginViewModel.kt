package com.example.dynamicformapp.feature.step.login.presentation

import android.util.Log
import com.example.dynamicformapp.feature.form.presentation.FormViewModel
import com.example.dynamicformapp.feature.step.login.domain.usecase.EmailFormUseCase
import com.example.dynamicformapp.feature.step.login.domain.usecase.NewsletterFormUseCase
import com.example.dynamicformapp.feature.step.login.domain.usecase.PasswordFormUseCase
import com.example.dynamicformapp.feature.step.login.domain.usecase.TermFormUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val emailFormUseCase: EmailFormUseCase,
    private val passwordFormUseCase: PasswordFormUseCase,
    private val termFormUseCase: TermFormUseCase,
    private val newsletterFormUseCase: NewsletterFormUseCase
) : FormViewModel() {

    private var email = ""
    private var password = ""
    private var newsletter = ""
    private var remeberPassword = false

    init {
        initForms(
            emailFormUseCase(::onOutput),
            passwordFormUseCase(::onOutput),
            termFormUseCase(::onOutput),
            *newsletterFormUseCase(::onOutput).toTypedArray()
        )
    }

    override fun setupValidations() {
        emailFormUseCase.onValidation { input, _ ->
            email = input.value
        }
        newsletterFormUseCase.onValidation { input, _ ->
            newsletter = input.value
        }
        passwordFormUseCase.onValidation { input, _ ->
            password = input.value
            remeberPassword = input.isSelected
        }
    }

    override fun getValidations(): Boolean =
        emailFormUseCase.isValid
            .and(passwordFormUseCase.isValid)
            .and(termFormUseCase.isValid)

    fun doLogin() {
        Log.d("WOW", "Mail: $email Password: $password Newsletter: $newsletter")
    }

    sealed class LoginState : FormState() {
        data class OnPasswordLengthRule(val isDone: Boolean) : LoginState()
        data class OnPasswordCharactersRule(val isDone: Boolean) : LoginState()
    }
}