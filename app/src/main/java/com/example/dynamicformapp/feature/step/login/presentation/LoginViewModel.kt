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
    private val emailForm: EmailFormUseCase,
    private val passwordForm: PasswordFormUseCase,
    private val termsForm: TermsFormUseCase,
    private val newsletterForm: NewsletterFormUseCase,
    private val cityForm: GenericFormUseCase,
    private val stateForm: GenericFormUseCase,
    private val stateAcronymForm: GenericFormUseCase,
) : FormViewModel() {

    private var email = ""
    private var password = ""
    private var newsletter = ""
    private var rememberPassword = false

    init {
        initForms(
            cityForm(::onOutput),
            stateForm(::onOutput),
            stateAcronymForm(::onOutput),
            emailForm(::onOutput),
            passwordForm(::onOutput),
            termsForm(::onOutput),
            *newsletterForm(::onOutput)
        )
    }

    override fun onSetupForms() {
        super.onSetupForms()
        emailForm.formVO.text = "wow@email.com"
        cityForm.formVO.text = "São Paulo"
        stateForm.formVO.text = "São Paulo"
        stateAcronymForm.formVO.text = "SP"
    }


    override fun setupValidations() {
        emailForm.onValidation { value, _, _ ->
            email = value
        }
        newsletterForm.onValidation { value, _, _ ->
            newsletter = value
        }
        passwordForm.onValidation { value, isSelected, ruleSet ->
            password = value
            rememberPassword = isSelected
            ruleSet?.rules?.let { loadRules(it) }
        }
    }

    override fun getValidations(): Boolean =
        emailForm.isValid
            .and(passwordForm.isValid)
            .and(termsForm.isValid)

    override val initialState: FormState = FormState.Init

    private fun loadRules(validations: List<FormRule>) {
        viewModelScope.launch(Dispatchers.Main) {
            setViewState(LoginState.OnLoadPasswordRules(validations))
        }
    }

    fun doLogin() {
        cityForm.formVO.text = "Osasco"
        Log.d("WOW", "Mail: $email Password: $password Newsletter: $newsletter")
        updateFormFields()
    }

    sealed class LoginState : FormState() {
        data class OnLoadPasswordRules(val rules: List<FormRule>?) : LoginState()
    }
}