package com.example.dynamicformapp.feature.step.login.domain

import android.util.Log
import com.example.dynamicformapp.feature.form.domain.BaseFormIteractor
import com.example.dynamicformapp.feature.form.domain.FormInteractor
import com.example.dynamicformapp.feature.form.model.FormRadioVO
import com.example.dynamicformapp.feature.form.model.FormVO
import com.example.dynamicformapp.feature.step.login.domain.usecase.EmailFormUseCase
import com.example.dynamicformapp.feature.step.login.domain.usecase.NewsletterFormUseCase
import com.example.dynamicformapp.feature.step.login.domain.usecase.PasswordFormUseCase
import com.example.dynamicformapp.feature.step.login.domain.usecase.TermFormUseCase
import javax.inject.Inject

interface StepAInteractor : FormInteractor {
    fun doLoginCall()
}

class StepAInteractorImpl @Inject constructor(
    private val emailFormUseCase: EmailFormUseCase,
    private val passwordFormUseCase: PasswordFormUseCase,
    private val termsUseCase: TermFormUseCase,
    private val newsLetterUseCase: NewsletterFormUseCase
) : BaseFormIteractor(), StepAInteractor {

    override var onValidate: ((Boolean) -> Unit) = {}
    override var onNotifyChangeAt: ((Int) -> Unit) = {}

    override var inputForms: ArrayList<FormVO> = arrayListOf(
        emailFormUseCase(::onOutput),
        passwordFormUseCase(::onOutput),
        termsUseCase(::onOutput),
    ).apply { addAll(newsLetterUseCase(::onOutput)) }

    override fun performValidation() {
        onValidate.invoke(
            emailFormUseCase.isValid && passwordFormUseCase.isValid && termsUseCase.isValid
        )
    }

    override fun doLoginCall() {
        Log.d("WOW", "Request|email: ${emailFormUseCase.email}")
        Log.d("WOW", "Request|password: ${passwordFormUseCase.password}")
        Log.d("WOW", "Request|passwordsave: ${passwordFormUseCase.shouldSavePassword}")
        Log.d("WOW", "Request|newsletter: ${newsLetterUseCase.newsLetterChoice}")
    }
}
