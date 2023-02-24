package com.example.dynamicformapp.feature.step.cpf.presentation

import com.example.dynamicformapp.feature.form.presentation.FormViewModel
import com.example.dynamicformapp.feature.step.cpf.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CpfViewModel @Inject constructor(
    private val cpfFormUseCase: CpfFormUseCase,
    private val termsFormUseCase: TermsFormUseCase
) : FormViewModel() {

    private var cpf = "423.658.228-78"

    override fun onLoadForms() = initForms(
        cpfFormUseCase(::onOutput),
        termsFormUseCase(::onOutput)
    )

    override fun onSetupForms() {
        cpfFormUseCase.formVO.text = cpf
    }

    override fun onValidations() {
        cpfFormUseCase.onValidation {
            cpf = it.value
        }
    }

    override fun getValidations(): Boolean =
        cpfFormUseCase.isValid().and(termsFormUseCase.isValid())
}