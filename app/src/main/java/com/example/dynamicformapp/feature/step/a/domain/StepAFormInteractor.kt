package com.example.dynamicformapp.feature.step.a.domain

import android.util.Log
import com.example.dynamicformapp.feature.form.domain.BaseFormIteractor
import com.example.dynamicformapp.feature.form.domain.FormInteractor
import com.example.dynamicformapp.feature.form.model.FormVO
import com.example.dynamicformapp.feature.step.a.domain.usecase.EmailFormUseCase
import com.example.dynamicformapp.feature.step.a.domain.usecase.PasswordFormUseCase
import com.example.dynamicformapp.feature.step.a.domain.usecase.SubscriptionFormUseCase
import com.example.dynamicformapp.feature.step.a.domain.usecase.TermFormUseCase
import javax.inject.Inject

interface StepAInteractor : FormInteractor {
    fun doLoginCall()
}

class StepAInteractorImpl @Inject constructor(
    private val emailFormUseCase: EmailFormUseCase,
    private val passwordFormUseCase: PasswordFormUseCase,
    private val termsUseCase: TermFormUseCase,
    private val subscribeFormUseCase: SubscriptionFormUseCase,
) : BaseFormIteractor(), StepAInteractor {

    override var onValidation: ((Boolean) -> Unit) = {}
    override var onNotifyUpdateAt: ((Int) -> Unit) = {}

    override var inputForms: ArrayList<FormVO> = arrayListOf(
        emailFormUseCase(::onOutput),
        passwordFormUseCase(::onOutput),
        termsUseCase(::onOutput),
    ).apply {
        addAll(subscribeFormUseCase(::onOutput))
    }

    override fun performValidation() {
        onValidation.invoke(
            emailFormUseCase.isValid && passwordFormUseCase.isValid && termsUseCase.isValid
        )
    }

    override fun doLoginCall() {
        Log.d("XABLAU", "Request|email: ${emailFormUseCase.email}")
        Log.d("XABLAU", "Request|password: ${passwordFormUseCase.password}")
        Log.d("XABLAU", "Request|passwordsave: ${passwordFormUseCase.shouldSavePassword}")
    }
}
