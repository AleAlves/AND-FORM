package com.example.dynamicformapp.feature.step.login.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.dynamicformapp.feature.form.domain.model.FormRule
import com.example.dynamicformapp.feature.form.presentation.FormViewModel
import com.example.dynamicformapp.feature.step.login.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val emailFormUseCase: EmailFormUseCase,
    private val passwordFormUseCase: PasswordFormUseCase,
    private val termFormUseCase: TermFormUseCase,
    private val newsletterFormUseCase: NewsletterFormUseCase,
    private val addressUseCase: AddressUseCase,
) : FormViewModel() {

    private var email = ""
    private var password = ""
    private var newsletter = ""
    private var rememberPassword = false

    init {
        initForms(
            addressUseCase(::onOutput),
            emailFormUseCase(::onOutput),
            passwordFormUseCase(::onOutput),
            termFormUseCase(::onOutput),
            *newsletterFormUseCase(::onOutput)
        )
    }

    override fun onSetupForms() {
        super.onSetupForms()
        emailFormUseCase.let {
            it.vo.text = "wow@email.com"
        }
    }


    override fun setupValidations() {
        emailFormUseCase.onValidation { value, _, _ ->
            email = value
        }
        newsletterFormUseCase.onValidation { value, _, _ ->
            newsletter = value
        }
        passwordFormUseCase.onValidation { value, check, rules ->
            password = value
            rememberPassword = check
            loadRules(rules?.rules)
        }
    }

    override fun getValidations(): Boolean =
        emailFormUseCase.isValid
            .and(passwordFormUseCase.isValid)
            .and(termFormUseCase.isValid)

    override val initialState: FormState = FormState.Init

    private fun loadRules(validations: List<FormRule>?) {
        viewModelScope.launch(Dispatchers.Main) {
            setViewState(LoginState.OnLoadPasswordRules(validations))
        }
    }

    fun doLogin() {
        Log.d("WOW", "Mail: $email Password: $password Newsletter: $newsletter")
    }

    sealed class LoginState : FormState() {
        data class OnLoadPasswordRules(val rules: List<FormRule>?) : LoginState()
    }
}