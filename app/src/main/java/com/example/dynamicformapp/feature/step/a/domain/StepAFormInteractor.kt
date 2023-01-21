package com.example.dynamicformapp.feature.step.a.domain

import android.util.Log
import com.example.dynamicformapp.feature.form.domain.BaseFormIteractor
import com.example.dynamicformapp.feature.form.domain.FormInteractor
import javax.inject.Inject

interface StepAInteractor : FormInteractor {
    fun doLoginCall()
}

class StepAInteractorImpl @Inject constructor(
    private val emailFormUseCase: EmailFormUseCase,
    private val passwordFormUseCase: PasswordFormUseCase,
    private val termsUseCase: TermFormUseCase,
) : BaseFormIteractor(), StepAInteractor {

    override var onValidation: ((Boolean) -> Unit) = {}
    override var onNotifyUpdateAt: ((Int) -> Unit) = {}

    override var inputForms = listOf(
        emailFormUseCase(::onReadInput),
        passwordFormUseCase(::onReadInput),
        termsUseCase(::onReadInput)
    )

    override fun performValidation() {
        onValidation.invoke(emailFormUseCase.isValid && passwordFormUseCase.isValid)
    }

    override fun doLoginCall() {
        Log.d("XABLAU", "Request|email: ${emailFormUseCase.email}")
        Log.d("XABLAU", "Request|password: ${passwordFormUseCase.password}")
        Log.d("XABLAU", "Request|passwordsave: ${passwordFormUseCase.shouldSavePassword}")
    }
}
